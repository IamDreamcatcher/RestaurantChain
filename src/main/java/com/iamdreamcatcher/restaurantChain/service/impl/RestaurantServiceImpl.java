package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.RestaurantDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.RestaurantMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.repository.RestaurantRepository;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AdministratorRepository administratorRepository;
    private final AuthContextHandler authContextHandler;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Iterable<RestaurantDTO> getRestaurants() {
        return restaurantMapper.toRestaurantDTOList(restaurantRepository.findAll());
    }

    @Override
    public Restaurant getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        return administrator.getRestaurant();
    }
}
