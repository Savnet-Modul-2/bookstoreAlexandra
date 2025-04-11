package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.entities.ReservationStatus;
import com.modul2.bookstore.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = """
            SELECT r FROM review r
            WHERE r.user.id = :userId
            """)
    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);
}