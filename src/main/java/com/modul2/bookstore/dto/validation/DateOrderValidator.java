package com.modul2.bookstore.dto.validation;

import com.modul2.bookstore.dto.ReservationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, ReservationDTO> {
    @Override
    public void initialize(ValidDateOrder validDateOrder) {
    }

    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        return reservationDTO.getStartDate().isBefore(reservationDTO.getEndDate());
    }
}