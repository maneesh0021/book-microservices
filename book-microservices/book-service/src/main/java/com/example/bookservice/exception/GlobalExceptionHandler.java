package com.example.bookservice.exception;

import com.example.bookservice.model.ErrorResponse;
import com.example.bookservice.util.ErrorResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex,
                                                            HttpServletRequest request) {
        return ErrorResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidBookDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookData(InvalidBookDataException ex,
                                                               HttpServletRequest request) {
        return ErrorResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex,
                                                                HttpServletRequest request) {
        return ErrorResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
