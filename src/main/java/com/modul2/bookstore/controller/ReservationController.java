package com.modul2.bookstore.controller;

import com.modul2.bookstore.entities.Reservation;
import com.modul2.bookstore.mapper.ReservationMapper;
import com.modul2.bookstore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userId") Long userId, @PathVariable(name = "bookId") Long bookId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Reservation createdReservation = reservationService.reserveBook(userId, bookId, startDate, endDate);
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(createdReservation));
    }
}
