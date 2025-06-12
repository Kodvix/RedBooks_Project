package com.org.kodvix.redbooks.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> buildError(HttpStatus status, String message, String path) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                status.value(),
                message,
                path
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(SchoolAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleSchoolExists(SchoolAlreadyExistException ex, WebRequest request) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> handleUserException(UserException ex, WebRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(RedBooksAPIException.class)
    public ResponseEntity<ErrorDetails> handleAPIException(RedBooksAPIException ex, WebRequest request) {
        return buildError(ex.getStatus(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return buildError(HttpStatus.CONFLICT, "Data integrity violation: " + ex.getMostSpecificCause().getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Validation failed");
        body.put("path", request.getDescription(false));
        body.put("errors", validationErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneric(Exception ex, WebRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), request.getDescription(false));
    }
}
