package com.cwpark.library.config.exception;

public class RuntimekakaoException extends RuntimeException {
    public RuntimekakaoException() {
        super();
    }

    public RuntimekakaoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RuntimekakaoException(Throwable cause) {
        super(cause);
    }
}
