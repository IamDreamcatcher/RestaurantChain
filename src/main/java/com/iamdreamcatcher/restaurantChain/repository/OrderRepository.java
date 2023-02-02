package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);
    List<Order> findOrdersByRestaurant(Restaurant restaurant);
    List<Order> findOrdersByCartClient(Client client);
    List<Order> findOrdersByRestaurantAndOrderStatus(Restaurant restaurant, OrderStatus orderStatus);
}
