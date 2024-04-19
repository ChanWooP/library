package com.cwpark.library.config.exception;

public class RuntimeNotSameUserException extends RuntimeException {
    public RuntimeNotSameUserException() {
        super();
    }

    public RuntimeNotSameUserException(String message) {
        super(message);
    }

}
