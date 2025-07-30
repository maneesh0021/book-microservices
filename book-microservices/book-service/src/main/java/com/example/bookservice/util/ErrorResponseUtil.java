package com.example.bookservice.util;

import com.example.bookservice.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponseUtil {

    private ErrorResponseUtil() {
        // prevent instantiation
    }

    public static ResponseEntity<ErrorResponse> buildErrorResponse(String message,
                                                                   HttpStatus status,
                                                                   HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
