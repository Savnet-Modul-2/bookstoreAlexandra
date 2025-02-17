package com.modul2.bookstore.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ErrorDetail error = new ErrorDetail(entityNotFoundException.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException) {
        ErrorDetail errorDetail = new ErrorDetail(invalidPasswordException.getMessage());
        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<?> handleAccountNotVerifiedException(AccountNotVerifiedException accountNotVerifiedException) {
        ErrorDetail errorDetail = new ErrorDetail(accountNotVerifiedException.getMessage());
        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
    }
}