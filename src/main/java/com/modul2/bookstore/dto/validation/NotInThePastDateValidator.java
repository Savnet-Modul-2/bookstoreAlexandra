package com.modul2.bookstore.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotInThePastDateValidator implements ConstraintValidator<ValidNotInThePastDate, LocalDate> {
    @Override
    public void initialize(ValidNotInThePastDate validNotInThePastDate) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date.isAfter(LocalDate.now());
    }
}