package com.modul2.bookstore.exceptions;

public class NoExemplaryAvailableException extends RuntimeException {
    public NoExemplaryAvailableException(String message) {
        super(message);
    }
}