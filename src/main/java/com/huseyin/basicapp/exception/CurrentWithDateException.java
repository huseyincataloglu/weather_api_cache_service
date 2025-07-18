package com.huseyin.basicapp.exception;

public class CurrentWithDateException extends RuntimeException {
    public CurrentWithDateException() {
        super("Current request parameter cannot be used with dates together.\n" +
                "It only returns current weather condition with related include specification");
    }
}
