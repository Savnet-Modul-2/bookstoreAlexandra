package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.ReservationDTO;
import com.modul2.bookstore.dto.validation.ValidationOrder;
import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.mapper.ReservationMapper;
import com.modul2.bookstore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userId") Long userId, @PathVariable(name = "bookId") Long bookId, @Validated(ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {
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

    @PutMapping("/update-reservation-status/{librarianId}/{reservationId}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long librarianId, @PathVariable Long reservationId, @RequestBody ReservationDTO reservationDTO){
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
