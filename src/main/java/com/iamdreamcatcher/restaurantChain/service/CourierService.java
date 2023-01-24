package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.employees.CourierDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

import java.util.List;

public interface CourierService {
    List<CourierDTO> getRestaurantCouriers() throws UserNotLoggedInException, NoPermissionException;
}
