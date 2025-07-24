package com.org.kodvix.redbooks.exception;

public class UnauthorizedOrderCancellationException extends RuntimeException {
    public UnauthorizedOrderCancellationException(String message) {
        super(message);
    }
}
