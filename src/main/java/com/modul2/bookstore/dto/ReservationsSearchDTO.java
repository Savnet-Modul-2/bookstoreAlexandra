package com.modul2.bookstore.dto;

import com.modul2.bookstore.entities.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public class ReservationsSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ReservationStatus> reservationStatusList;

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

    public List<ReservationStatus> getReservationStatusList() {
        return reservationStatusList;
    }

    public void setReservationStatusList(List<ReservationStatus> reservationStatusList) {
        this.reservationStatusList = reservationStatusList;
    }
}