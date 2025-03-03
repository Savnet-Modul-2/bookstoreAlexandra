package com.modul2.bookstore.dto;

import com.modul2.bookstore.entities.ReservationStatus;

import java.time.LocalDate;

public class ReservationDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;
    private UserDTO user;
    private ExemplaryDTO exemplary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ExemplaryDTO getExemplary() {
        return exemplary;
    }

    public void setExemplary(ExemplaryDTO exemplary) {
        this.exemplary = exemplary;
    }
}
