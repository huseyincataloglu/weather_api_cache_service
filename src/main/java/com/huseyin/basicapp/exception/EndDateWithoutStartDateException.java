package com.huseyin.basicapp.exception;

public class EndDateWithoutStartDateException extends RuntimeException {
    public EndDateWithoutStartDateException(String message) {
        super(message);
    }
}
