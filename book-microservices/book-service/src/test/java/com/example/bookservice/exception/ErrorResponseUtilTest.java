package com.example.bookservice.exception;

import com.example.bookservice.model.ErrorResponse;
import com.example.bookservice.util.ErrorResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseUtilTest {

    @Test
    void testBuildErrorResponse() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/books/test");

        ResponseEntity<ErrorResponse> response = ErrorResponseUtil.buildErrorResponse(
                "Test message", HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test message", response.getBody().getMessage());
        assertEquals("Bad Request", response.getBody().getError());
        assertEquals("/books/test", response.getBody().getPath());
    }
}
