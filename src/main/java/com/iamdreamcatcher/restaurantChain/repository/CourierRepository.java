package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findByUser(User user);
}
