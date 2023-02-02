package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.ReviewDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ReviewRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

public interface ReviewsService {
    Iterable<ReviewDTO> getRestaurantReviews() throws UserNotLoggedInException, NoPermissionException;

    ReviewDTO getReviewById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    ReviewDTO leaveComment(Long id, ReviewRequestDTO reviewRequestDto) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    void deleteReviewById(Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException;

    void leaveReview(Long id, ReviewDTO reviewDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException;
}
