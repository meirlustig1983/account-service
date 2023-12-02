package com.ml.accountservice.manager;

import com.ml.accountservice.dto.AccountField;
import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.dto.TokenInfo;
import com.ml.accountservice.exceptions.AccountNotFoundException;
import com.ml.accountservice.exceptions.CreationAccountException;
import com.ml.accountservice.mapper.AccountMapper;
import com.ml.accountservice.mapper.TokenMapper;
import com.ml.accountservice.model.Account;
import com.ml.accountservice.model.Token;
import com.ml.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MockitoSettings
class AccountManagerTest {

    @Mock
    private AccountInfo accountInfo;

    @Mock
    private Account account;

    @Mock
    private TokenInfo tokenInfo;

    @Mock
    private Token token;

    @Mock
    private AccountService service;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private TokenMapper tokenMapper;

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

        when(accountMapper.toAccount(accountInfo)).thenReturn(account);
        when(service.save(account)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.createAccount(accountInfo);

        assertThat(result).isEqualTo(accountInfo);

        verify(accountMapper).toAccount(accountInfo);
        verify(service).save(account);
        verify(accountMapper).toAccountInfo(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
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

        when(accountMapper.toAccount(accountInfo)).thenReturn(account);
        when(service.save(account)).thenReturn(Optional.empty());

        assertThrows(CreationAccountException.class, () -> manager.createAccount(accountInfo));

        verify(accountMapper).toAccount(accountInfo);
        verify(service).save(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
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
        when(accountMapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.getAccountByEmail("email");

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByEmail("email");
        verify(accountMapper).toAccountInfo(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void getAccountByEmail_ServiceFailed() {

        when(service.getAccountByEmail("email")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.getAccountByEmail("email"));

        verify(service).getAccountByEmail("email");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
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
        when(accountMapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.getAccountByPhoneNumber("phone");

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByPhoneNumber("phone");
        verify(accountMapper).toAccountInfo(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void getAccountByPhoneNumber_ServiceFailed() {
        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.getAccountByPhoneNumber("phone"));

        verify(service).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void updateToken_ByEmail() {
        when(service.getAccountByEmail("email")).thenReturn(Optional.of(account));
        when(tokenMapper.toToken(tokenInfo)).thenReturn(token);
        when(service.save(account)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.updateToken("email", AccountField.EMAIL, tokenInfo);

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByEmail("email");
        verify(service).save(account);
        verify(tokenMapper).toToken(tokenInfo);
        verify(accountMapper).toAccountInfo(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void updateToken_ByEmail_ServiceFailed() {
        when(service.getAccountByEmail("email")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.updateToken("email", AccountField.EMAIL, tokenInfo));

        verify(service).getAccountByEmail("email");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void updateToken_ByPhoneNumber() {
        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.of(account));
        when(tokenMapper.toToken(tokenInfo)).thenReturn(token);
        when(service.save(account)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountInfo(account)).thenReturn(accountInfo);

        AccountInfo result = manager.updateToken("phone", AccountField.PHONE_NUMBER, tokenInfo);

        assertThat(result).isEqualTo(accountInfo);

        verify(service).getAccountByPhoneNumber("phone");
        verify(service).save(account);
        verify(tokenMapper).toToken(tokenInfo);
        verify(accountMapper).toAccountInfo(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void updateToken_ByPhoneNumber_ServiceFailed() {
        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.updateToken("phone", AccountField.PHONE_NUMBER, tokenInfo));

        verify(service).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void deleteAccount_ByEmail() {
        when(service.getAccountByEmail("email")).thenReturn(Optional.of(account));

        manager.deleteAccount("email", AccountField.EMAIL);

        verify(service).getAccountByEmail("email");
        verify(service).delete(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void deleteAccount_ByEmail_ServiceFailed() {
        when(service.getAccountByEmail("email")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.deleteAccount("email", AccountField.EMAIL));

        verify(service).getAccountByEmail("email");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void deleteAccount_ByPhoneNumber() {
        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.of(account));

        manager.deleteAccount("phone", AccountField.PHONE_NUMBER);

        verify(service).getAccountByPhoneNumber("phone");
        verify(service).delete(account);
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }

    @Test
    public void deleteAccount_ByPhoneNumber_ServiceFailed() {
        when(service.getAccountByPhoneNumber("phone")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> manager.deleteAccount("phone", AccountField.PHONE_NUMBER));

        verify(service).getAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(accountMapper, tokenMapper, service);
    }
}