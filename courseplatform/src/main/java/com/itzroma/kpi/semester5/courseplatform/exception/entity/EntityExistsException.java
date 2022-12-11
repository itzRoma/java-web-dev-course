package com.itzroma.kpi.semester5.courseplatform.exception.entity;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }
}
