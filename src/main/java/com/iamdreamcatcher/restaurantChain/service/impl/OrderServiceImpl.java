package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.OrderDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.OrderMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ClientService clientService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AdministratorService administratorService;
    private final CookService cookService;
    private final CourierService courierService;

    @Override
    public Iterable<OrderDTO> getOrdersForClient() throws UserNotLoggedInException, NoPermissionException {
        Client client = clientService.getClient();
        return orderMapper.toOrderDTOList(orderRepository.findOrdersByCartClient(client));
    }

    @Override
    public OrderDTO getOrderForClient(Long id) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        Client client = clientService.getClient();
        Order order = orderRepository.findOrderById(id);
        if (!client.equals(order.getCart().getClient())) {
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
        if (!administrator.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to admin");
        }

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus orderStatus) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Order order = orderRepository.findOrderById(id);
        if (!administrator.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to admin");
        }
        order.setOrderStatus(orderStatus);
    }

    @Override
    public Iterable<OrderDTO> getOrdersForCook() throws UserNotLoggedInException, NoPermissionException {
        Cook cook = cookService.getCook();
        Restaurant restaurant = cook.getRestaurant();

        return orderMapper.toOrderDTOList(orderRepository.findOrdersByRestaurantAndOrderStatus(restaurant, OrderStatus.ACCEPTED));
    }

    @Override
    public OrderDTO getOrderForCook(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Cook cook = cookService.getCook();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !cook.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to cook");
        }

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public void startCooking(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Cook cook = cookService.getCook();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !cook.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to cook");
        }
        if (order.getOrderStatus() != OrderStatus.ACCEPTED) {
            throw new NotFoundException("This order doesn't exist");
        }

        order.setOrderStatus(OrderStatus.COOKING);
        orderRepository.save(order);
    }

    @Override
    public void finishingCooking(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Cook cook = cookService.getCook();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !cook.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to cook");
        }
        if (order.getOrderStatus() != OrderStatus.COOKING) {
            throw new NotFoundException("This order doesn't exist");
        }

        order.setOrderStatus(OrderStatus.PREPARED);
        orderRepository.save(order);
    }

    @Override
    public Iterable<OrderDTO> getOrdersForCourier() throws UserNotLoggedInException, NoPermissionException {
        Courier courier = courierService.getCourier();
        Restaurant restaurant = courier.getRestaurant();

        return orderMapper.toOrderDTOList(orderRepository.findOrdersByRestaurantAndOrderStatus(restaurant, OrderStatus.ACCEPTED));
    }

    @Override
    public OrderDTO getOrderForCourier(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Courier courier = courierService.getCourier();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !courier.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to courier");
        }

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public void delivered(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Courier courier = courierService.getCourier();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !courier.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to courier");
        }
        if (order.getOrderStatus() != OrderStatus.PREPARED) {
            throw new NotFoundException("This order doesn't exist");
        }

        order.setOrderStatus(OrderStatus.DELIVERY);
        orderRepository.save(order);
    }

    @Override
    public void completed(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Courier courier = courierService.getCourier();
        Order order = orderRepository.findOrderById(id);
        if (order == null || !courier.getRestaurant().equals(order.getRestaurant())) {
            throw new NotFoundException("This order doesn't belong to courier");
        }
        if (order.getOrderStatus() != OrderStatus.DELIVERY) {
            throw new NotFoundException("This order doesn't exist");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
