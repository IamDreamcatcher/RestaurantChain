package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.ReviewDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ReviewRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.ReviewMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.reviews.Review;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.repository.ReviewsRepository;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import com.iamdreamcatcher.restaurantChain.service.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final OrderRepository orderRepository;
    private final AdministratorService administratorService;
    private final ClientService clientService;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDTO> getRestaurantReviews() throws UserNotLoggedInException, NoPermissionException {
        Administrator administrator = administratorService.getAdmin();
        List<Order> orders = orderRepository.findOrdersByRestaurant(administrator.getRestaurant());
        List<Review> reviews = reviewsRepository.findReviewsByOrderIn(orders);

        return reviewMapper.toReviewDTOList(reviews);
    }

    @Override
    public ReviewDTO getReviewById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (!administrator.getRestaurant().equals(review.getOrder().getRestaurant())) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public ReviewDTO leaveComment(Long id, ReviewRequestDTO reviewRequestDto) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (!administrator.getRestaurant().equals(review.getOrder().getRestaurant())) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        review.setAdministratorComment(reviewRequestDto.administratorComment());
        return reviewMapper.toReviewDTO(review);
    }

    @Override
    public void deleteReviewById(Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Review review = reviewsRepository.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review with this index doesn't exist");
        }
        if (!administrator.getRestaurant().equals(review.getOrder().getRestaurant())) {
            throw new NoPermissionException("This review doesn't belong to this admin");
        }

        reviewsRepository.deleteById(id);
    }

    @Override
    public void leaveReview(Long id, ReviewDTO reviewDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Client client = clientService.getClient();
        Order order = orderRepository.findOrderById(reviewDTO.getOrderDTo().getId());
        if (order == null || !order.getCart().getClient().equals(client)) {
            throw new NotFoundException("Order doesn't belong to the client");
        }

        Review review = new Review();
        review.setMessage(reviewDTO.getMessage());
        review.setOrder(order);
        reviewsRepository.save(review);
    }
}
