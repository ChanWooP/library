package com.cwpark.library.config.exception;

public class RuntimeEntityNotFoundException extends RuntimeException {
    public RuntimeEntityNotFoundException() {
        super();
    }

    public RuntimeEntityNotFoundException(String message) {
        super(message);
    }
}
