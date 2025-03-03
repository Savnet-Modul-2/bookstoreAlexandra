package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.*;
import com.modul2.bookstore.repository.BookRepository;
import com.modul2.bookstore.repository.ExemplaryRepository;
import com.modul2.bookstore.repository.ReservationRepository;
import com.modul2.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDate;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Reservation reserveBook(Long userId, Long bookId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);


        Exemplary availableExemplary = exemplaryRepository
                .findAvailableExemplary(bookId, startDate, endDate)
                .orElseThrow(EntityNotFoundException::new);


        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        user.addReservation(reservation);
        availableExemplary.addReservation(reservation);

        return reservationRepository.save(reservation);
    }
}
