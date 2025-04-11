package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Review;
import com.modul2.bookstore.entities.User;
import com.modul2.bookstore.repository.BookRepository;
import com.modul2.bookstore.repository.ReviewRepository;
import com.modul2.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Review create(Review review) {
        if (review.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new review that you want to create");
        }
        return reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Page<Review> findAll(Long userId, Integer pageNumber, Integer pageSize) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "data"));
        return reviewRepository.findReviewsByUserId(userId, pageable);
    }

    public Review updateById(Long userId, Long reviewId, Review reviewEntity) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
        Book book = review.getBook();

        review.setNota(reviewEntity.getNota());
        review.setDescriere(reviewEntity.getDescriere());
        review.setData(reviewEntity.getData());

        book.calculMedie();
        bookRepository.save(book);
        return reviewRepository.save(review);
    }

    public void deleteById(Long userId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
        Book book = review.getBook();

        book.removeReview(review);
        book.calculMedie();
        user.removeReview(review);

        bookRepository.save(book);
        userRepository.save(user);

        reviewRepository.deleteById(reviewId);
    }
}
