package com.org.kodvix.redbooks.exception;

public class CustomerDetailsNotFoundException extends RuntimeException{
    public CustomerDetailsNotFoundException(String message) {
        super(message);
    }
}
