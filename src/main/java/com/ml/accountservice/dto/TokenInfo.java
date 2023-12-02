package com.ml.accountservice.dto;

import com.ml.accountservice.model.TokenType;

import java.time.LocalDateTime;

public record TokenInfo(String token, String serviceName, TokenType type, boolean active, LocalDateTime expirationDate) {
}