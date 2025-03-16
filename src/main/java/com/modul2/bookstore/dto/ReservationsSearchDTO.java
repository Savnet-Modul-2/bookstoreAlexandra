package com.modul2.bookstore.dto;

import com.modul2.bookstore.dto.validation.AdvancedValidation;
import com.modul2.bookstore.dto.validation.BasicValidation;
import com.modul2.bookstore.dto.validation.DateNotInThePast;
import com.modul2.bookstore.dto.validation.ValidDateOrder;
import com.modul2.bookstore.entities.ReservationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@ValidDateOrder(groups = AdvancedValidation.class)
public class ReservationsSearchDTO {
    @NotNull(groups = BasicValidation.class)
    private LocalDate startDate;
    @NotNull(groups = BasicValidation.class)
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