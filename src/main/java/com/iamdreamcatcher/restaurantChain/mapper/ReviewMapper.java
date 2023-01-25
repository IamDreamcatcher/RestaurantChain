package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.ReviewDTO;
import com.iamdreamcatcher.restaurantChain.model.reviews.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    List<ReviewDTO> toReviewDTOList(List<Review> reviewList);
    ReviewDTO toReviewDTO(Review review);
}
