package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.OrderDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.OrderMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import com.iamdreamcatcher.restaurantChain.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ClientService clientService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AdministratorService administratorService;
    @Override
    public Iterable<OrderDTO> getOrdersForClient() throws UserNotLoggedInException, NoPermissionException {
        Client client = clientService.getClient();
        return orderMapper.toOrderDTOList(orderRepository.findOrdersByCartClient(client));
    }

    @Override
    public OrderDTO getOrderForClient(Long id) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        Client client = clientService.getClient();
        Order order = orderRepository.findOrderById(id);
        if (client.getId() != order.getCart().getClient().getId()) {
            throw new NotFoundException("This order doesn't belong to this client");
        }

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public Iterable<OrderDTO> getOrdersForAdmin() throws UserNotLoggedInException, NoPermissionException {
        Administrator administrator = administratorService.getAdmin();

        return orderMapper.toOrderDTOList(orderRepository.findOrdersByRestaurant(administrator.getRestaurant()));
    }

    @Override
    public OrderDTO getOrderForAdmin(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Order order = orderRepository.findOrderById(id);
        if (administrator.getRestaurant().getId() != order.getRestaurant().getId()) {
            throw new NotFoundException("This order doesn't belong to admin");
        }

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus orderStatus) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Order order = orderRepository.findOrderById(id);
        if (administrator.getRestaurant().getId() != order.getRestaurant().getId()) {
            throw new NotFoundException("This order doesn't belong to admin");
        }
        order.setOrderStatus(orderStatus);
    }
}
