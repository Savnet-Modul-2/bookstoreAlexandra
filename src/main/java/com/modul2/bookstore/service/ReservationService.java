package com.modul2.bookstore.service;

import com.modul2.bookstore.dto.ReservationsSearchDTO;
import com.modul2.bookstore.entities.*;
import com.modul2.bookstore.exceptions.MissingArgumentException;
import com.modul2.bookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private EmailService emailService;

    //@Transactional - face rollback la primul save de exemplary, daca se arunca o exceptie in cadrul metodei
    public Reservation reserveBook(Long userId, Long bookId, Reservation reservation) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        Exemplary availableExemplary = exemplaryRepository
                .findAvailableExemplary(bookId, reservation.getStartDate(), reservation.getEndDate())
                .orElseThrow(EntityNotFoundException::new);

        if (availableExemplary.getMaxBorrowDays() < Duration.between(reservation.getStartDate().atStartOfDay(), reservation.getStartDate().atStartOfDay()).toDays()) {
            throw new UnsupportedOperationException("Reservation too long, must be maximum " + availableExemplary.getMaxBorrowDays() + " days");
        }

        availableExemplary.setUpdateTime(LocalDateTime.now());

        user.addReservation(reservation);
        availableExemplary.addReservation(reservation);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        //aici pun debug
//        exemplaryRepository.save(availableExemplary);

        return reservationRepository.save(reservation);
    }

    public Reservation getById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservationStatus(Long librarianId, Long reservationId, Reservation reservation) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(EntityNotFoundException::new);

        Reservation newReservation = reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);

        if (!Objects.equals(librarian.getLibrary().getId(), newReservation.getExemplary().getBook().getLibrary().getId())) {
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
        reservationRepository.saveAll(reservations);

        reservations.forEach(reservation -> {
            String userEmail = reservation.getUser().getEmail();
            String userPhone = reservation.getUser().getPhoneNumber();
            String bookTitle = reservation.getExemplary().getBook().getTitle();
            String librarianEmail = reservation.getExemplary().getBook().getLibrary().getLibrarian().getEmail();

            String userSubject = "Book to return";
            String userText = " You need to return the book '" + bookTitle + "'.";
            emailService.sendEmail(userEmail, userSubject, userText);

            String librarianSubject = "Unreturned Book";
            String librarianText = "The user " + reservation.getUser().getFirstName() + " "
                    + reservation.getUser().getLastName() +
                    " with phone number " + userPhone + " has not returned the book '" + bookTitle +
                    "'. Please contact them.";
            emailService.sendEmail(librarianEmail, librarianSubject, librarianText);
        });

        return reservations;
    }

    public Page<Reservation> getLibraryReservationsByStartDateAndEndDate(Long libraryId, Integer pageNumber, Integer pageSize, ReservationsSearchDTO reservationsSearchDTO) throws MissingArgumentException {
        if (reservationsSearchDTO.getStartDate() == null || reservationsSearchDTO.getEndDate() == null) {
            throw new MissingArgumentException("Start date or end date is missing");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByStartDateAndEndDate(libraryId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getReservationStatusList(), pageable);
    }

    public Page<Reservation> getUserReservationsByStatus(Long userId, Integer pageNumber, Integer pageSize, ReservationsSearchDTO reservationsSearchDTO) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByUserAndReservationStatus(userId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getReservationStatusList(), pageable);
    }
}