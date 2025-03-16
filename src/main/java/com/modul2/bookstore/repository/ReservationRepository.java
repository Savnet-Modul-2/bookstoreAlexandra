package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.entities.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationStatusAndStartDateBefore(ReservationStatus reservationStatus, LocalDate date);

    List<Reservation> findByReservationStatusAndEndDateBefore(ReservationStatus reservationStatus, LocalDate date);

    @Query(value = """
            SELECT r FROM reservation r
            WHERE (r.startDate >= :startDate AND r.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR r.reservationStatus IN :reservationStatusList)
            AND r.exemplary.book.library.id = :libraryId
            """)
    Page<Reservation> findReservationsByStartDateAndEndDate(Long libraryId, LocalDate startDate, LocalDate endDate, List<ReservationStatus> reservationStatusList, Pageable pageable);

    @Query(value = """
            SELECT r FROM reservation r
            WHERE r.user.id = :userId
            AND (cast(:startDate as date) IS NULL OR r.startDate >= :startDate) 
            AND (cast(:endDate as date) IS NULL OR r.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR r.reservationStatus IN :reservationStatusList)
            """)
    Page<Reservation> findReservationsByUserAndReservationStatus(Long userId, LocalDate startDate, LocalDate endDate, List<ReservationStatus> reservationStatusList, Pageable pageable);
}