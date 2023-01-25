package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

public interface CartService {
    void addProductToShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException;
}
