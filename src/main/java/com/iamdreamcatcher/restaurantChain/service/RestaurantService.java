package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.RestaurantDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;

import java.util.List;

public interface RestaurantService {
    Iterable<RestaurantDTO> getRestaurants();

    Restaurant getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException;
}
