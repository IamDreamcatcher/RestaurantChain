package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.OrderDTO;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    List<OrderDTO> toOrderDTOList(List<Order> orders);

    OrderDTO toOrderDTO(Order order);
}