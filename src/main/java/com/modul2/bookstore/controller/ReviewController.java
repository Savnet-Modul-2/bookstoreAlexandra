package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.ReviewDTO;
import com.modul2.bookstore.entities.Review;
import com.modul2.bookstore.mapper.ReviewMapper;
import com.modul2.bookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewDTO reviewDTO) {
        Review review = ReviewMapper.reviewDto2Review(reviewDTO);
        Review createdReview = reviewService.create(review);
        return ResponseEntity.ok(ReviewMapper.review2ReviewDto(createdReview));
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> findAll(@PathVariable Long userId,
                                     @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Review> reviewPage = reviewService.findAll(userId, pageNumber, pageSize);
            return ResponseEntity.ok(reviewPage.map(ReviewMapper::review2ReviewDto));
        }
        List<Review> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews.stream()
                .map(ReviewMapper::review2ReviewDto)
                .toList());
    }

    @PutMapping("/{userId}/update-review/{reviewId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "userId") Long userId, @PathVariable(name = "reviewId") Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        Review reviewEntity = ReviewMapper.reviewDto2Review(reviewDTO);
        Review updatedReview = reviewService.updateById(userId, reviewId, reviewEntity);
        return ResponseEntity.ok(ReviewMapper.review2ReviewDto(updatedReview));
    }

    @DeleteMapping("/{userId}/delete-review/{reviewId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "userId") Long userId, @PathVariable(name = "reviewId") Long reviewId) {
        reviewService.deleteById(userId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
