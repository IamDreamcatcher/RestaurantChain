package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookRepository extends JpaRepository<Cook, Long> {
    Cook findByUser(User user);
    Cook findCookById(Long id);
    List<Cook> findAllCooksByRestaurant(Restaurant restaurant);
}
