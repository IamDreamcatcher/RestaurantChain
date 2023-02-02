package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.RestaurantDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.users.ClientDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.RestaurantMapper;
import com.iamdreamcatcher.restaurantChain.mapper.users.ClientMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.repository.RestaurantRepository;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AdministratorService administratorService;
    private final RestaurantMapper restaurantMapper;
    private final OrderRepository orderRepository;
    private final ClientMapper clientMapper;

    @Override
    public Iterable<RestaurantDTO> getRestaurants() {
        return restaurantMapper.toRestaurantDTOList(restaurantRepository.findAll());
    }

    @Override
    public Restaurant getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException {
        Administrator administrator = administratorService.getAdmin();
        return administrator.getRestaurant();
    }

    @Override
    public Iterable<ClientDTO> getRestaurantClients() throws UserNotLoggedInException, NoPermissionException {
        Administrator administrator = administratorService.getAdmin();
        List<Order> orders = orderRepository.findOrdersByRestaurant(administrator.getRestaurant());
        List<Client> clients = new ArrayList<>();
        for (Order order : orders) {
            if (!clients.contains(order.getCart().getClient())) {
                clients.add(order.getCart().getClient());
            }
        }

        return clientMapper.toClientDtoList(clients);
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        return restaurantMapper.toRestaurantDTO(restaurantRepository.findRestaurantById(id));
    }
}
