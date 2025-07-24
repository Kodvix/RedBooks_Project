package com.org.kodvix.redbooks.exception;

public class BookAlreadyAssignedException extends RuntimeException{
    public BookAlreadyAssignedException(String message) {
        super(message);
    }
}
