package com.huseyin.basicapp.exception;

public class StartDateEqualsEndDateException extends RuntimeException {
    public StartDateEqualsEndDateException() {
        super("Start and end dates cannot be equal");
    }
}
