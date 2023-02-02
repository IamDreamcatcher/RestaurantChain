package com.iamdreamcatcher.restaurantChain.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iamdreamcatcher.restaurantChain.dto.model.users.AdministratorDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.users.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.users.CookDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.users.CourierDTO;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    private OrderStatus orderStatus;
    private CartDTO cartDTO;
    private CourierDTO courierDTO;
    private CookDTO cookDTO;
    private RestaurantDTO restaurantDTO;
    private Double price;
    private String address;
}