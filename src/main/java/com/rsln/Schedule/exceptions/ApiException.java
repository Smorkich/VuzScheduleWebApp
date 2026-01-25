package com.rsln.Schedule.exceptions;

public abstract class ApiException extends RuntimeException {

    protected ApiException(String message) {
        super(message);
    }
}
