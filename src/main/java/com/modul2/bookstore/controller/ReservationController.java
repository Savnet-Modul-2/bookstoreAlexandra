package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.ReservationDTO;
import com.modul2.bookstore.dto.ReservationsSearchDTO;
import com.modul2.bookstore.dto.validation.ValidationOrder;
import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.exceptions.MissingArgumentException;
import com.modul2.bookstore.mapper.ReservationMapper;
import com.modul2.bookstore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "bookId") Long bookId,
                                         @Validated(ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.reserveBook(userId, bookId, ReservationMapper.reservationDTO2Reservation(reservationDTO));
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(createdReservation));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getById(@PathVariable(name = "reservationId") Long reservationId) {
        Reservation reservation = reservationService.getById(reservationId);
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(reservation));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Reservation> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations.stream()
                .map(ReservationMapper::reservation2ReservationDTO)
                .toList());
    }

    @GetMapping("/library/{libraryId}")
    public ResponseEntity<?> getLibraryReservationsByStartDateAndEndDate(@PathVariable(name = "libraryId") Long libraryId,
                                                                         @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @Validated(ValidationOrder.class) @RequestBody ReservationsSearchDTO reservationsSearchDTO) throws MissingArgumentException {
        Page<Reservation> reservationPage = reservationService.getLibraryReservationsByStartDateAndEndDate(libraryId, pageNumber, pageSize, reservationsSearchDTO);
        return ResponseEntity.ok(reservationPage.map(ReservationMapper::reservation2ReservationDTO));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserReservationsByStatus(@PathVariable(name = "userId") Long userId,
                                                         @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @Validated(ValidationOrder.class) @RequestBody ReservationsSearchDTO reservationsSearchDTO){
        Page<Reservation> reservationPage = reservationService.getUserReservationsByStatus(userId, pageNumber, pageSize, reservationsSearchDTO);
        return ResponseEntity.ok(reservationPage.map(ReservationMapper::reservation2ReservationDTO));
    }

    @PutMapping("/update-reservation-status/{librarianId}/{reservationId}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long librarianId,
                                                     @PathVariable Long reservationId,
                                                     @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.updateReservationStatus(librarianId, reservationId, ReservationMapper.reservationDTO2Reservation(reservationDTO));
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(reservation));
    }

    @PutMapping
    public ResponseEntity<?> checkExpiredReservations() {
        List<Reservation> canceledReservations = reservationService.updateExpiredPendingReservations();
        List<Reservation> delayedReservations = reservationService.updateExpiredInProgressReservations();

        List<Reservation> updatedReservations = new ArrayList<>();
        updatedReservations.addAll(canceledReservations);
        updatedReservations.addAll(delayedReservations);

        return ResponseEntity.ok(updatedReservations.stream()
                .map(ReservationMapper::reservation2ReservationDTO)
                .toList());
    }
}