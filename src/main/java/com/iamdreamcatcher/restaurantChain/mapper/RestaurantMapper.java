package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.RestaurantDTO;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    List<RestaurantDTO> toRestaurantDTOList(List<Restaurant> restaurantList);
}
