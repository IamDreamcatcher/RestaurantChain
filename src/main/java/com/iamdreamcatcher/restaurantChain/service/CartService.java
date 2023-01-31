package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.CartDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

public interface CartService {
    void addProductToShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException, NoPermissionException;

    void removeProductFromShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException, NoPermissionException;

    CartDTO getShoppingCart() throws UserNotLoggedInException, NotFoundException, NoPermissionException;

    void clearShoppingCart() throws UserNotLoggedInException, NotFoundException, NoPermissionException;

    void checkout() throws UserNotLoggedInException, NotFoundException, NoPermissionException;
}
