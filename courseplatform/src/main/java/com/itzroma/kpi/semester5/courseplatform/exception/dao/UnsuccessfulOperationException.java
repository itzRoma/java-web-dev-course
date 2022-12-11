package com.itzroma.kpi.semester5.courseplatform.exception.dao;

public class UnsuccessfulOperationException extends RuntimeException {
    public UnsuccessfulOperationException(String message) {
        super(message);
    }

    public UnsuccessfulOperationException(Throwable cause) {
        super(cause);
    }
}
