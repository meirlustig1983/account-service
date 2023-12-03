package com.ml.accountservice.schedulers;

import com.ml.accountservice.messages.Message;
import com.ml.accountservice.messages.MessageType;
import com.ml.accountservice.messages.Producer;
import com.ml.accountservice.model.Account;
import com.ml.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.Mockito.*;

@MockitoSettings
class AccountCleanUpSchedulerTest {

    private static final String TOPIC = "topic";

    @Mock
    private Account account;

    @Mock
    private AccountService accountService;

    @Mock
    private Producer producer;

    @InjectMocks
    private AccountCleanUpScheduler scheduler;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(scheduler, "topic", TOPIC);
    }

    @Test
    public void apply(){
        Message message = new Message().setType(MessageType.ACCOUNT).setId("id");

        when(account.getId()).thenReturn("id");
        when(accountService.getAll()).thenReturn(List.of(account));

        scheduler.apply();

        verify(accountService).getAll();
        verify(producer).sendMessage(TOPIC, message);
        verifyNoMoreInteractions(accountService, producer);
    }

    @Test
    public void apply_EmptyList(){
        when(accountService.getAll()).thenReturn(List.of());

        scheduler.apply();

        verify(accountService).getAll();
        verifyNoMoreInteractions(accountService, producer);
    }
}