package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.CartDTO;
import com.iamdreamcatcher.restaurantChain.model.cart.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    List<CartDTO> toCartDTOList(List<Cart> cart);

    CartDTO toCartDTO(Cart cart);
}