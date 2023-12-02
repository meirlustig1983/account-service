package com.ml.accountservice.exceptions;

public class UpdateAccountException extends RuntimeException {
    public UpdateAccountException() {
        super("Failed to update account data");
    }
}
