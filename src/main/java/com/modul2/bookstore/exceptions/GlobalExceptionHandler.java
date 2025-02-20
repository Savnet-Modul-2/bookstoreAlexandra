package com.modul2.bookstore.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ErrorDetail error = new ErrorDetail(entityNotFoundException.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityExistsException entityExistsException) {
        ErrorDetail errorDetail = new ErrorDetail(entityExistsException.getMessage());
        return new ResponseEntity<>(errorDetail, CONFLICT);
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