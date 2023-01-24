package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.employees.CookDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.CookRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

import java.util.List;

public interface CookService {
    List<CookDTO> getRestaurantCooks() throws UserNotLoggedInException, NoPermissionException;
    CookDTO getCookById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    CookDTO createCookAccount(CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException;

    CookDTO updateCookAccount(Long id, CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException;
}
