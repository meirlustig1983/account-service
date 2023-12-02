package com.ml.accountservice.controller;

import com.ml.accountservice.dto.AccountField;
import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.dto.AccountRequest;
import com.ml.accountservice.exceptions.CreationAccountException;
import com.ml.accountservice.exceptions.InternalServerException;
import com.ml.accountservice.manager.AccountManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class AccountControllerTest {

    @Mock
    private AccountManager manager;

    @InjectMocks
    private AccountController controller;

    @Test
    public void createAccount() {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(manager.createAccount(accountInfo)).thenReturn(accountInfo);

        ResponseEntity<AccountInfo> result = controller.createAccount(accountInfo);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(accountInfo);

        verify(manager).createAccount(accountInfo);
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void createAccount_ManagerFailed() {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(manager.createAccount(accountInfo)).thenReturn(null);

        assertThrows(CreationAccountException.class, () -> controller.createAccount(accountInfo));

        verify(manager).createAccount(accountInfo);
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void getAccountByEmail() {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        AccountRequest request = new AccountRequest("email", AccountField.EMAIL);

        when(manager.getAccountByEmail("email")).thenReturn(accountInfo);

        ResponseEntity<AccountInfo> result = controller.getAccount(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(accountInfo);

        verify(manager).getAccountByEmail("email");
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void getAccountByPhoneNumber() {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        AccountRequest request = new AccountRequest("phone", AccountField.PHONE_NUMBER);

        when(manager.getAccountByPhoneNumber("phone")).thenReturn(accountInfo);

        ResponseEntity<AccountInfo> result = controller.getAccount(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(accountInfo);

        verify(manager).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(manager);
    }


    @Test
    public void getAccountByEmail_ManagerFailed() {
        AccountRequest request = new AccountRequest("email", AccountField.EMAIL);

        when(manager.getAccountByEmail("email")).thenReturn(null);

        assertThrows(InternalServerException.class, () -> controller.getAccount(request));

        verify(manager).getAccountByEmail("email");
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void getAccountByPhoneNumber_ManagerFailed() {
        AccountRequest request = new AccountRequest("phone", AccountField.PHONE_NUMBER);

        when(manager.getAccountByPhoneNumber("phone")).thenReturn(null);

        assertThrows(InternalServerException.class, () -> controller.getAccount(request));

        verify(manager).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(manager);
    }

}