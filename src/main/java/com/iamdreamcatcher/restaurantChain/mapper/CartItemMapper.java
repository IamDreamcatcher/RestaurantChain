package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.model.cartItem.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    List<CartItemDTO> toCartItemDTOList(List<CartItem> cartItems);

    CartItemDTO toCartItemDTO(CartItem cartItems);
}