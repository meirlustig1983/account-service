package com.ml.accountservice.exceptions;

public class CreationDataException extends RuntimeException {
    public CreationDataException() {
        super("Failed to create account");
    }
}