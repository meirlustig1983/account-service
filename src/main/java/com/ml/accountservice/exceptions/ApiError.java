package com.ml.accountservice.exceptions;

public record ApiError(String path, String message, int statusCode) {
}
