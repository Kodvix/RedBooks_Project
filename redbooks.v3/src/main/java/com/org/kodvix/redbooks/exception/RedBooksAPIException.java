package com.org.kodvix.redbooks.exception;

import org.springframework.http.HttpStatus;

public class RedBooksAPIException extends RuntimeException {
    private final HttpStatus status;

    public RedBooksAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
