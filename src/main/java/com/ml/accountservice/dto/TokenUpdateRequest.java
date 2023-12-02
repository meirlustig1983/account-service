package com.ml.accountservice.dto;

public record TokenUpdateRequest(String value, AccountField field, TokenInfo tokenInfo) {
}
