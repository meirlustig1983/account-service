package com.ml.accountservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Token implements Serializable {

    private String token;

    private String serviceName;

    private TokenType type;

    private boolean active;

    private LocalDateTime expirationDate;

    @CreatedDate
    private LocalDateTime creationDate;
}