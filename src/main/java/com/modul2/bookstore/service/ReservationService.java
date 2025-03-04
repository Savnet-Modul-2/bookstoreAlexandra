package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.*;
import com.modul2.bookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private LibrarianRepository librarianRepository;

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

    public Reservation getById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Reservation updateReservationStatus(Long librarianId, Long reservationId, Reservation reservation) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(EntityNotFoundException::new);

        Reservation newReservation = reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);

        if(!Objects.equals(librarian.getLibrary().getId(), newReservation.getExemplary().getBook().getLibrary().getId())){
            throw new IllegalArgumentException("Librarian does not have access to the library!");
        }

        if (!newReservation.getReservationStatus().isNextStatePossible(reservation.getReservationStatus())) {
            throw new EntityNotFoundException("Cannot update status of reservation");
        }

        newReservation.setReservationStatus(reservation.getReservationStatus());
        return reservationRepository.save(newReservation);
    }

    public List<Reservation> updateExpiredPendingReservations() {
        List<Reservation> reservations = reservationRepository.findByReservationStatusAndStartDateBefore(ReservationStatus.PENDING, LocalDate.now());
        reservations.forEach(reservation -> reservation.setReservationStatus(ReservationStatus.CANCELED));
        return reservationRepository.saveAll(reservations);
    }

    public List<Reservation> updateExpiredInProgressReservations() {
        List<Reservation> reservations = reservationRepository.findByReservationStatusAndEndDateBefore(ReservationStatus.IN_PROGRESS, LocalDate.now());
        reservations.forEach(reservation -> reservation.setReservationStatus(ReservationStatus.DELAYED));
        return reservationRepository.saveAll(reservations);
        // notificări către librarian și user
    }
}
