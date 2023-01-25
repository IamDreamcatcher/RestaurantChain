package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findCartItemById(Long id);
}