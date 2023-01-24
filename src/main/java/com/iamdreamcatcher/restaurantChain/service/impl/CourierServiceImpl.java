package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.employees.CourierDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.CourierMapper;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.repository.CourierRepository;
import com.iamdreamcatcher.restaurantChain.service.CourierService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final RestaurantService restaurantService;
    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    @Override
    public List<CourierDTO> getRestaurantCouriers() throws UserNotLoggedInException, NoPermissionException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        List<Courier> couriers = courierRepository.findAllCouriersByRestaurant(restaurant);

        return courierMapper.toCourierListDto(couriers);
    }
}
