package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    Review findReviewByOrder(Order order);
    List<Review> findReviewsByOrderIn(List<Order> orders);

    Review findReviewById(Long id);
}
