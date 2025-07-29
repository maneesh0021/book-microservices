package com.example.bookservice.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book with the given ID not found.");
    }
}
