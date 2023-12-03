package com.ml.accountservice.schedulers;

import com.ml.accountservice.messages.Message;
import com.ml.accountservice.messages.MessageType;
import com.ml.accountservice.messages.Producer;
import com.ml.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AccountCleanUpScheduler {

    private final AccountService accountService;

    private final Producer producer;

    private final String topic;

    public AccountCleanUpScheduler(AccountService accountService, Producer producer,
                                   @Value("${spring.kafka.producer.topic}") String topic) {
        this.accountService = accountService;
        this.producer = producer;
        this.topic = topic;
    }

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void myScheduledTask() {
        log.info("Scheduled task executed, type: 'AccountCleanUpScheduler', at: {}", LocalDateTime.now());
        accountService.getAll().forEach(account -> {
            log.info("Send message, topic: '{}', type: 'ACCOUNT', id: '{}'", topic, account.getId());
            producer.sendMessage(topic, new Message().setType(MessageType.ACCOUNT).setId(account.getId()));
        });
    }
}