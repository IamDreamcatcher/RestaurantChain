package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.cart.Cart;
import com.iamdreamcatcher.restaurantChain.model.cart.CartStatus;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartById(Long id);
    Cart findFirstCartByClientAndCartStatus(Client client, CartStatus cartStatus);
}