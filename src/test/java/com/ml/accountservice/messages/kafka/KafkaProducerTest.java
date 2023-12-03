package com.ml.accountservice.messages.kafka;

import com.ml.accountservice.messages.Message;
import com.ml.accountservice.messages.MessageType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@MockitoSettings
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @InjectMocks
    private KafkaProducer producer;

    @Test
    public void sendMessage() {
        Message message = new Message().setId("id").setType(MessageType.ACCOUNT);
        producer.sendMessage("topic", message);
        verify(kafkaTemplate).send("topic", message);
    }
}