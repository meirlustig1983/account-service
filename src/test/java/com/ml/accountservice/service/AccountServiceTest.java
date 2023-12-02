package com.ml.accountservice.service;

import com.ml.accountservice.model.Account;
import com.ml.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
class AccountServiceTest {

    @Mock
    private Cache cache;

    @Mock
    private AccountRepository repository;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private AccountService service;

    @Test
    public void save() {

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(repository.save(account)).thenReturn(account);
        when(cacheManager.getCache("account")).thenReturn(cache);

        Optional<Account> result = service.save(account);

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(true);
        assertThat(result.get().getId()).isEqualTo("id");
        assertThat(result.get().getFirstName()).isEqualTo("first");
        assertThat(result.get().getLastName()).isEqualTo("last");
        assertThat(result.get().getEmail()).isEqualTo("email");
        assertThat(result.get().getPhoneNumber()).isEqualTo("phone");

        verify(repository).save(account);
        verify(cacheManager, times(2)).getCache("account");
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void delete() {

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(cacheManager.getCache("account")).thenReturn(cache);

        service.delete(account);

        verify(repository).delete(account);
        verify(cacheManager, times(2)).getCache("account");
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void save_RepositoryFailed() {

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(repository.save(account)).thenReturn(null);

        Optional<Account> result = service.save(account);

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(false);

        verify(repository).save(account);
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void getAccountByEmail() {

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(repository.findAccountByEmail("email")).thenReturn(account);

        Optional<Account> result = service.getAccountByEmail("email");

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(true);
        assertThat(result.get().getId()).isEqualTo("id");
        assertThat(result.get().getFirstName()).isEqualTo("first");
        assertThat(result.get().getLastName()).isEqualTo("last");
        assertThat(result.get().getEmail()).isEqualTo("email");
        assertThat(result.get().getPhoneNumber()).isEqualTo("phone");

        verify(repository).findAccountByEmail("email");
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void getAccountByEmail_RepositoryFailed() {

        when(repository.findAccountByEmail("email")).thenReturn(null);

        Optional<Account> result = service.getAccountByEmail("email");

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(false);

        verify(repository).findAccountByEmail("email");
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void getAccountByPhoneNumber() {

        Account account = new Account()
                .setId("id")
                .setFirstName("first")
                .setLastName("last")
                .setEmail("email")
                .setPhoneNumber("phone")
                .setCreationDate(LocalDateTime.now());

        when(repository.findAccountByPhoneNumber("phone")).thenReturn(account);

        Optional<Account> result = service.getAccountByPhoneNumber("phone");

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(true);
        assertThat(result.get().getId()).isEqualTo("id");
        assertThat(result.get().getFirstName()).isEqualTo("first");
        assertThat(result.get().getLastName()).isEqualTo("last");
        assertThat(result.get().getEmail()).isEqualTo("email");
        assertThat(result.get().getPhoneNumber()).isEqualTo("phone");

        verify(repository).findAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(repository, cacheManager);
    }

    @Test
    public void getAccountByPhoneNumber_RepositoryFailed() {

        when(repository.findAccountByPhoneNumber("phone")).thenReturn(null);

        Optional<Account> result = service.getAccountByPhoneNumber("phone");

        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isEqualTo(false);

        verify(repository).findAccountByPhoneNumber("phone");
        verifyNoMoreInteractions(repository, cacheManager);
    }
}