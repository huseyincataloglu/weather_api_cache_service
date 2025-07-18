package com.huseyin.basicapp.exception;

public class StartDateMustBeBeforeException extends RuntimeException {
    public StartDateMustBeBeforeException() {
        super("Start date must be before than endDate");
    }
}
