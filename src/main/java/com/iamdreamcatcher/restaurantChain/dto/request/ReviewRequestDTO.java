package com.iamdreamcatcher.restaurantChain.dto.request;

import com.iamdreamcatcher.restaurantChain.dto.model.OrderDTO;

public record ReviewRequestDTO(
        OrderDTO orderDTO,
        String message,
        String administratorComment
) {

}