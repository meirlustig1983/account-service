package com.ml.accountservice.controller;

import com.ml.accountservice.dto.*;
import com.ml.accountservice.exceptions.AccountNotFoundException;
import com.ml.accountservice.exceptions.CreationAccountException;
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
    private TokenInfo tokenInfo;

    @Mock
    private TokenUpdateRequest tokenUpdateRequest;

    @Mock
    private AccountRequest accountRequest;

    @Mock
    private AccountInfo accountInfo;

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

        when(manager.createAccount(accountInfo)).thenThrow(CreationAccountException.class);

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

        when(manager.getAccountByEmail("email")).thenThrow(AccountNotFoundException.class);

        assertThrows(AccountNotFoundException.class, () -> controller.getAccount(request));

        verify(manager).getAccountByEmail("email");
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void getAccountByPhoneNumber_ManagerFailed() {
        AccountRequest request = new AccountRequest("phone", AccountField.PHONE_NUMBER);

        when(manager.getAccountByPhoneNumber("phone")).thenThrow(AccountNotFoundException.class);

        assertThrows(AccountNotFoundException.class, () -> controller.getAccount(request));

        verify(manager).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void updateToken() {
        when(tokenUpdateRequest.value()).thenReturn("email");
        when(tokenUpdateRequest.field()).thenReturn(AccountField.EMAIL);
        when(tokenUpdateRequest.tokenInfo()).thenReturn(tokenInfo);
        when(manager.updateToken("email", AccountField.EMAIL, tokenInfo)).thenReturn(accountInfo);

        ResponseEntity<AccountInfo> result = controller.updateToken(tokenUpdateRequest);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(accountInfo);

        verify(manager).updateToken("email", AccountField.EMAIL, tokenInfo);
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void updateToken_ManagerFailed() {
        when(tokenUpdateRequest.value()).thenReturn("email");
        when(tokenUpdateRequest.field()).thenReturn(AccountField.EMAIL);
        when(tokenUpdateRequest.tokenInfo()).thenReturn(tokenInfo);
        when(manager.updateToken("email", AccountField.EMAIL, tokenInfo)).thenThrow(AccountNotFoundException.class);

        assertThrows(AccountNotFoundException.class, () -> controller.updateToken(tokenUpdateRequest));

        verify(manager).updateToken("email", AccountField.EMAIL, tokenInfo);
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void deleteAccount() {
        when(accountRequest.value()).thenReturn("email");
        when(accountRequest.field()).thenReturn(AccountField.EMAIL);

        controller.deleteAccount(accountRequest);

        verify(manager).deleteAccount("email", AccountField.EMAIL);
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void deleteAccount_ManagerFailed() {
        when(accountRequest.value()).thenReturn("email");
        when(accountRequest.field()).thenReturn(AccountField.EMAIL);

        doThrow(AccountNotFoundException.class).when(manager).deleteAccount("email", AccountField.EMAIL);

        assertThrows(AccountNotFoundException.class, () -> controller.deleteAccount(accountRequest));

        verify(manager).deleteAccount("email", AccountField.EMAIL);
        verifyNoMoreInteractions(manager);
    }
}