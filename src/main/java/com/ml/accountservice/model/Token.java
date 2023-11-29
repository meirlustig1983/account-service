package com.ml.accountservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Accessors(chain = true)
@Data
public class Token {

    private String token;

    private String serviceName;

    private TokenType type;

    private boolean active;

    @CreatedDate
    private LocalDateTime creationDate;
}