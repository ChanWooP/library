package com.cwpark.library.config.exception;

public class RuntimeEmailException extends RuntimeException {
    public RuntimeEmailException() {
        super();
    }

    public RuntimeEmailException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RuntimeEmailException(Throwable cause) {
        super(cause);
    }
}
