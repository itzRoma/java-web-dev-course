package com.itzroma.kpi.semester5.courseplatform.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
