package com.ml.accountservice.exceptions;

public class UpdateDataException extends RuntimeException {
    public UpdateDataException() {
        super("Failed to update account data");
    }
}
