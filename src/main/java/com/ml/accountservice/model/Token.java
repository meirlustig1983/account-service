package com.ml.accountservice.model;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return Objects.equal(getServiceName(), token.getServiceName()) && getType() == token.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getServiceName(), getType());
    }
}