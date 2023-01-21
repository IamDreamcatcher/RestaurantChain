package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.repository.RestaurantRepository;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MainPageController {
    private final RestaurantService restaurantService;
//    private final AdministratorRepository administratorRepository;
//    private final RestaurantRepository restaurantRepository;
//    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<?> getRestaurants() {
//        Administrator administrator = new Administrator();
//        User user = userRepository.findByEmail("admin@mail.ru");
//        user.setRole(Role.ADMIN);
//        administrator.setUser(user);
//        administrator.setNumber("+375298549386");
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("aboba");
//        restaurant.setAddress("minsk");
//        administrator.setRestaurant(restaurant);
//        restaurantRepository.save(restaurant);
//        userRepository.save(user);
//        administratorRepository.save(administrator);
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getRestaurants()));
    }
    //To do:add profile with possibility to change
}
