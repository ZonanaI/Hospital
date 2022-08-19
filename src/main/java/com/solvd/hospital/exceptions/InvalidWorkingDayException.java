package com.solvd.hospital.exceptions;

public class InvalidWorkingDayException extends RuntimeException {
    public InvalidWorkingDayException(String message) {
        super(message);
    }
}
