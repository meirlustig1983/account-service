package com.ml.accountservice.service;

import com.ml.accountservice.model.Account;
import com.ml.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "accounts", allEntries = true)
    @CachePut(value = "account", key = "#account.email")
    public Optional<Account> save(Account account) {
        return optional(repository.save(account));
    }

    @Cacheable(value = "account", key = "#email", unless = "#result == null")
    public Optional<Account> getAccountByEmail(String email) {
        return optional(repository.findAccountByEmail(email));
    }

    @Cacheable(value = "account", key = "#phoneNumber", unless = "#result == null")
    public Optional<Account> getAccountByPhoneNumber(String phoneNumber) {
        return optional(repository.findAccountByPhoneNumber(phoneNumber));
    }

    private Optional<Account> optional(Account account) {
        if (account != null) {
            return Optional.of(account);
        } else {
            return Optional.empty();
        }
    }
}
