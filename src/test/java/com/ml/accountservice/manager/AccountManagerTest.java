package com.ml.accountservice.manager;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.mapper.AccountMapper;
import com.ml.accountservice.model.Account;
import com.ml.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
class AccountManagerTest {

    @Mock
    private AccountService service;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountManager manager;

    @Test
    public void createAccount() {

        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(mapper.toAccount(accountInfo)).thenReturn(account);
        when(service.save(account)).thenReturn(Optional.of(account));
        when(mapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.createAccount(accountInfo);

        assertThat(result).isEqualTo(accountInfo);

        verify(mapper).toAccount(accountInfo);
        verify(service).save(account);
        verify(mapper).toAccountInfo(account);
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    public void createAccount_ServiceFailed() {

        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(mapper.toAccount(accountInfo)).thenReturn(account);
        when(service.save(account)).thenReturn(Optional.empty());

        AccountInfo result = manager.createAccount(accountInfo);

        assertThat(result).isNull();

        verify(mapper).toAccount(accountInfo);
        verify(service).save(account);
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    public void getAccountByEmail() {

        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(service.getAccountByEmail("email")).thenReturn(Optional.of(account));
        when(mapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.getAccountByEmail("email");

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByEmail("email");
        verify(mapper).toAccountInfo(account);
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    public void getAccountByEmail_ServiceFailed() {

        when(service.getAccountByEmail("email")).thenReturn(Optional.empty());

        AccountInfo result = manager.getAccountByEmail("email");

        assertThat(result).isNull();

        verify(service).getAccountByEmail("email");
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    public void getAccountByPhoneNumber() {

        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.of(account));
        when(mapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.getAccountByPhoneNumber("phone");

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByPhoneNumber("phone");
        verify(mapper).toAccountInfo(account);
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    public void getAccountByPhoneNumber_ServiceFailed() {

        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.empty());

        AccountInfo result = manager.getAccountByPhoneNumber("phone");

        assertThat(result).isNull();

        verify(service).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(mapper, service);
    }

}