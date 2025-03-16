package com.modul2.bookstore.dto.validation;

import com.modul2.bookstore.dto.ReservationDTO;
import com.modul2.bookstore.dto.ReservationsSearchDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, Object> {
    @Override
    public void initialize(ValidDateOrder validDateOrder) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (obj instanceof ReservationDTO) {
            ReservationDTO dto = (ReservationDTO) obj;
            startDate = dto.getStartDate();
            endDate = dto.getEndDate();
        } else if (obj instanceof ReservationsSearchDTO) {
            ReservationsSearchDTO dto = (ReservationsSearchDTO) obj;
            startDate = dto.getStartDate();
            endDate = dto.getEndDate();
        }

        if (startDate == null || endDate == null) {
            return true;
        }

        return startDate.isBefore(endDate);
    }
}