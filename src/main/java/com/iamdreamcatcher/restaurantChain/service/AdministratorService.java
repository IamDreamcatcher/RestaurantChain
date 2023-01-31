package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;

public interface AdministratorService {
    Administrator getAdmin() throws UserNotLoggedInException, NoPermissionException;
}
