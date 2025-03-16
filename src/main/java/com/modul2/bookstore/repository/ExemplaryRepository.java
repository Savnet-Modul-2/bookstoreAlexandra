package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Exemplary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Long> {
    @Query(value = """
            SELECT * FROM exemplary e
            WHERE e.id NOT IN (
                SELECT r.exemplary_id FROM reservation r
                WHERE ((r.start_date <= :endDate AND r.end_date >= :startDate)
                AND r.reservation_status IN ('IN_PROGRESS', 'PENDING')) 
                OR r.reservation_status = 'DELAYED'
            )
            AND e.book_id = :bookId
            LIMIT 1
            """, nativeQuery = true)
    Optional<Exemplary> findAvailableExemplary(Long bookId, LocalDate startDate, LocalDate endDate);
}