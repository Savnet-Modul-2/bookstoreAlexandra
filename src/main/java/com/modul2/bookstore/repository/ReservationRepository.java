package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationStatusAndStartDateBefore(ReservationStatus reservationStatus, LocalDate startDate);
    List<Reservation> findByReservationStatusAndEndDateBefore(ReservationStatus reservationStatus, LocalDate endDate);
}
