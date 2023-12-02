package com.ml.accountservice.exceptions;

public class CreationAccountException extends RuntimeException {
    public CreationAccountException() {
        super("Failed to create account");
    }
}