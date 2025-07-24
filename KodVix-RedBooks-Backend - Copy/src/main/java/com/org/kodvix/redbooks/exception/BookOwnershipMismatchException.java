package com.org.kodvix.redbooks.exception;

public class BookOwnershipMismatchException extends RuntimeException {
    public BookOwnershipMismatchException(String message) {
        super(message);
    }
}
