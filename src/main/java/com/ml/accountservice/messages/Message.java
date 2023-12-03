package com.ml.accountservice.messages;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Message implements Serializable {

    private MessageType type;

    private String id;
}