package com.cwpark.library.exception;

public class ErrorException extends RuntimeException {
    public ErrorException() {
        super();
    }

    public ErrorException(String message) {
        super(message);
    }
}
