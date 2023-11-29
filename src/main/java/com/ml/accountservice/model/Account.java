package com.ml.accountservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Accessors(chain = true)
@Data
public class Account implements Serializable {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String phoneNumber;

    private List<Token> tokens;

    @CreatedDate
    private LocalDateTime creationDate;
}
