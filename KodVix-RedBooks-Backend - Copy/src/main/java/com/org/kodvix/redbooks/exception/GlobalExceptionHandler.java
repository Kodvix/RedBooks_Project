package com.org.kodvix.redbooks.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleGeneric(Exception ex, WebRequest request) {
//        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), request.getDescription(false));
//    }
    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateBookException(DuplicateBookException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(DuplicateClassException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateClassException(DuplicateClassException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(BookAlreadyAssignedException.class)
    public ResponseEntity<Map<String, String>> handleBookAlreadyAssignedException(BookAlreadyAssignedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSchoolNotFoundException(SchoolNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    @ExceptionHandler(OtpValidationException.class)
    public ResponseEntity<Map<String, String>> handleOtpValidationException(OtpValidationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(InvalidClassAccessException.class)
    public ResponseEntity<Map<String, String>> handleInvalidClassAccess(InvalidClassAccessException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBookNotFound(BookNotFoundException ex) {
        return simpleMessage(ex.getMessage());
    }

    @ExceptionHandler(BookOwnershipMismatchException.class)
    public ResponseEntity<Map<String, String>> handleOwnershipMismatch(BookOwnershipMismatchException ex) {
        return simpleMessage(ex.getMessage());
    }

    @ExceptionHandler(BookTitleMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTitleMismatch(BookTitleMismatchException ex) {
        return simpleMessage(ex.getMessage());
    }
    @ExceptionHandler(ClassNotFoundCustomException.class)
    public ResponseEntity<Map<String, String>> handleClassNotFound(ClassNotFoundCustomException ex) {
        return simpleMessage(ex.getMessage());
    }

    @ExceptionHandler(ClassOwnershipException.class)
    public ResponseEntity<Map<String, String>> handleClassOwnership(ClassOwnershipException ex) {
        return simpleMessage(ex.getMessage());
    }
    @ExceptionHandler(ClassMismatchException.class)
    public ResponseEntity<Map<String, String>> handleClassMismatch(ClassMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(CustomerDetailsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerDetailsNotFound(CustomerDetailsNotFoundException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(UnauthorizedOrderCancellationException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedCancel(UnauthorizedOrderCancellationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(CustomerClassNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerClassNotFound(CustomerClassNotFoundException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(ClassNotFoundForSchoolException.class)
    public ResponseEntity<Map<String, String>> handleClassNotFoundForSchool(ClassNotFoundForSchoolException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidPasswordException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }


    private ResponseEntity<Map<String, String>> simpleMessage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }




}

