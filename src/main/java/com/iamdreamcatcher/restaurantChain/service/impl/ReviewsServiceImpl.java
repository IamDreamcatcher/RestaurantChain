package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.ReviewDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ReviewRequestDto;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.ReviewMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.reviews.Review;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.repository.ReviewsRepository;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final AuthContextHandler authContextHandler;
    private final OrderRepository orderRepository;
    private final AdministratorRepository administratorRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDTO> getRestaurantReviews() throws UserNotLoggedInException, NoPermissionException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        List<Order> orders = orderRepository.findOrdersByAdministrator(administrator);
        List<Review> reviews = reviewsRepository.findReviewsByOrderIn(orders);

        return reviewMapper.toReviewDTOList(reviews);
    }

    @Override
    public ReviewDTO getReviewById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (administrator.getId() != review.getOrder().getAdministrator().getId()) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public ReviewDTO leaveComment(Long id, ReviewRequestDto reviewRequestDto) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (administrator.getId() != review.getOrder().getAdministrator().getId()) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        review.setAdministratorComment(reviewRequestDto.administratorComment());
        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public void deleteReviewById(Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (administrator.getId() != review.getOrder().getAdministrator().getId()) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        reviewsRepository.deleteById(id);
    }
}
