package com.ml.accountservice.messages;

public interface Producer {
    void sendMessage(String topic, Message message);
}
