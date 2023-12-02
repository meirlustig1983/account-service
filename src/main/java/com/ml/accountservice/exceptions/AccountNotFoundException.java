package com.ml.accountservice.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Failed to find account");
    }
}