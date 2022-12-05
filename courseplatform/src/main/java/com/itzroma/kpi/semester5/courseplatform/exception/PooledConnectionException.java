package com.itzroma.kpi.semester5.courseplatform.exception;

public class PooledConnectionException extends RuntimeException {
    public PooledConnectionException(String message) {
        super(message);
    }

    public PooledConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
