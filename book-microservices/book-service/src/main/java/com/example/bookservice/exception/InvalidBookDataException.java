package com.example.bookservice.exception;

public class InvalidBookDataException extends RuntimeException {

    public InvalidBookDataException() {
        super("Invalid book data provided.");
    }

    public InvalidBookDataException(String message) {
        super(message);
    }
}
