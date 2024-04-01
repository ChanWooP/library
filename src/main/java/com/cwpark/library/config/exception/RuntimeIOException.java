package com.cwpark.library.config.exception;

public class RuntimeIOException extends RuntimeException{
    /**
     *
     */
    public RuntimeIOException() {
        super();
    }

    /**
     * @param message
     */
    public RuntimeIOException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RuntimeIOException(Throwable cause) {
        super(cause);
    }
}
