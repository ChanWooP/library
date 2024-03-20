package com.cwpark.library.exception;

public class RuntimeEntityNotFoundException extends RuntimeException {
    public RuntimeEntityNotFoundException() {
        super();
    }

    public RuntimeEntityNotFoundException(String message) {
        super(message);
    }
}
