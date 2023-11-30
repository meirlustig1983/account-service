package com.ml.accountservice.manager;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.mapper.AccountMapper;
import com.ml.accountservice.model.Account;
import com.ml.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountManager {

    private final AccountService service;

    private final AccountMapper mapper;

    @Autowired
    public AccountManager(AccountService service, AccountMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public AccountInfo createAccount(AccountInfo accountInfo) {
        Account account = mapper.toAccount(accountInfo);
        Optional<Account> optional = service.save(account);
        return optional.map(mapper::toAccountInfo).orElse(null);
    }

    public AccountInfo getAccountByEmail(String email) {
        Optional<Account> optional = service.getAccountByEmail(email);
        return optional.map(mapper::toAccountInfo).orElse(null);
    }

    public AccountInfo getAccountByPhoneNumber(String phoneNumber) {
        Optional<Account> optional = service.getAccountByPhoneNumber(phoneNumber);
        return optional.map(mapper::toAccountInfo).orElse(null);
    }
}
