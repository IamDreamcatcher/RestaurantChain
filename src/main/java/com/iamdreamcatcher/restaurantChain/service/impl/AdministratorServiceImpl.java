package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
    private final AuthContextHandler authContextHandler;
    private final AdministratorRepository administratorRepository;

    @Override
    public Administrator getAdmin() throws UserNotLoggedInException, NoPermissionException {
        User user = authContextHandler.getLoggedInUser();
        Administrator admin = administratorRepository.findByUser(user);
        if (admin == null) {
            throw new NoPermissionException("User is not a admin");
        }
        return admin;
    }
}
