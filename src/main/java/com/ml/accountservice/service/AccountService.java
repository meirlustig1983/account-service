package com.ml.accountservice.service;

import com.ml.accountservice.model.Account;
import com.ml.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    private final CacheManager cacheManager;

    private final AccountRepository repository;

    @Autowired
    public AccountService(CacheManager cacheManager, AccountRepository repository) {
        this.cacheManager = cacheManager;
        this.repository = repository;
    }

    public Optional<Account> save(Account account) {
        if (account.getCreationDate() == null) {
            account.setCreationDate(LocalDateTime.now());
        }
        account.setUpdateDate(LocalDateTime.now());
        Optional<Account> savedAccount = optional(repository.save(account));

        savedAccount.ifPresent(saved -> cacheManager.getCache("account").put(saved.getEmail(), saved));
        savedAccount.ifPresent(saved -> cacheManager.getCache("account").put(saved.getPhoneNumber(), saved));

        return savedAccount;
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
