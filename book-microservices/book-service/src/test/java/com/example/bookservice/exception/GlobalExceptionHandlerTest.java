package com.example.bookservice.exception;

import com.example.bookservice.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void testHandleBookNotFound() {
        Mockito.when(request.getRequestURI()).thenReturn("/books/1");
        BookNotFoundException ex = new BookNotFoundException("Book not found");

        ResponseEntity<ErrorResponse> response = handler.handleBookNotFound(ex, request);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book not found", response.getBody().getMessage());
        assertEquals("/books/1", response.getBody().getPath());
    }

    @Test
    void testHandleInvalidBookData() {
        Mockito.when(request.getRequestURI()).thenReturn("/books");
        InvalidBookDataException ex = new InvalidBookDataException("Invalid data");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidBookData(ex, request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid data", response.getBody().getMessage());
    }

    @Test
    void testHandleGenericException() {
        Mockito.when(request.getRequestURI()).thenReturn("/books/unknown");
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex, request);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}
