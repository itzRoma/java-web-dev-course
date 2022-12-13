package com.itzroma.kpi.semester5.courseplatform.exception.entity;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(Throwable cause) {
        super(cause);
    }
}
