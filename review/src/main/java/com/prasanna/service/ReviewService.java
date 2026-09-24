package com.prasanna.service;

import com.prasanna.modal.Review;
import com.prasanna.payload.dto.ReviewRequest;
import com.prasanna.payload.dto.SalonDTO;
import com.prasanna.payload.dto.UserDTO;

import java.util.List;

public interface ReviewService {
    Review createReview(
            ReviewRequest req,
            UserDTO user,
            SalonDTO salon
    );

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(ReviewRequest req,Long reviewId,Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}

