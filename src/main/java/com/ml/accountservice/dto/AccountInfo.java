package com.ml.accountservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class AccountInfo {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDateTime creationDate;
}