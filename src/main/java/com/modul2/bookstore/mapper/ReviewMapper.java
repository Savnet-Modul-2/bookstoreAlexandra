package com.modul2.bookstore.mapper;

import com.modul2.bookstore.dto.ReviewDTO;
import com.modul2.bookstore.entities.Review;

public class ReviewMapper {
    public static Review reviewDto2Review(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setNota(reviewDTO.getNota());
        review.setDescriere(reviewDTO.getDescriere());
        review.setData(reviewDTO.getData());

        return review;
    }

    public static ReviewDTO review2ReviewDto(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setNota(review.getNota());
        reviewDTO.setDescriere(review.getDescriere());
        reviewDTO.setData(review.getData());

        return reviewDTO;
    }
}
