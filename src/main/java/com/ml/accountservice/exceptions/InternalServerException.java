package com.ml.accountservice.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
        super("Failed to get account data");
    }
}
