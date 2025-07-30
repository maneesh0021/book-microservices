package com.example.bookservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = new MockHttpServletRequest();
        mockRequest.setRequestURI("/books/42");
    }

    @Test
    void testHandleBookNotFound() {
        BookNotFoundException ex = new BookNotFoundException("Book ID not found in database");
        ResponseEntity<Map<String, Object>> response = handler.handleBookNotFound(ex, mockRequest);

        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals("Book ID not found in database", body.get("message"));
        assertEquals("/books/42", body.get("path"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new RuntimeException("Something went wrong");
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex, mockRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("Something went wrong", body.get("message"));
        assertEquals("/books/42", body.get("path"));
        assertNotNull(body.get("timestamp"));
    }
}
