package com.ml.accountservice.messages.kafka;

import com.ml.accountservice.messages.Message;
import com.ml.accountservice.messages.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer implements Producer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, Message message) {
        kafkaTemplate.send(topic, message);
    }
}