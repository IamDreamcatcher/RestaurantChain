package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.users.CourierDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.CourierRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

public interface CourierService {
    Iterable<CourierDTO>getRestaurantCouriers() throws UserNotLoggedInException, NoPermissionException;

    CourierDTO getCourierById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    CourierDTO createCourierAccount(CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException;

    CourierDTO updateCourierAccount(Long id, CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    void deleteCourierById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException;
}
