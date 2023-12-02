package com.ml.accountservice.manager;

import com.ml.accountservice.dto.AccountField;
import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.dto.TokenInfo;
import com.ml.accountservice.exceptions.AccountNotFoundException;
import com.ml.accountservice.exceptions.CreationAccountException;
import com.ml.accountservice.exceptions.UpdateAccountException;
import com.ml.accountservice.mapper.AccountMapper;
import com.ml.accountservice.mapper.TokenMapper;
import com.ml.accountservice.model.Account;
import com.ml.accountservice.model.Token;
import com.ml.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class AccountManager {

    private final AccountService service;

    private final AccountMapper accountMapper;

    private final TokenMapper tokenMapper;

    @Autowired
    public AccountManager(AccountService service, AccountMapper accountMapper, TokenMapper tokenMapper) {
        this.service = service;
        this.accountMapper = accountMapper;
        this.tokenMapper = tokenMapper;
    }

    public AccountInfo createAccount(AccountInfo accountInfo) {
        Account account = accountMapper.toAccount(accountInfo);
        Optional<Account> optional = service.save(account);
        return optional.map(accountMapper::toAccountInfo).orElseThrow(CreationAccountException::new);
    }

    public AccountInfo getAccountByEmail(String email) {
        Optional<Account> optional = service.getAccountByEmail(email);
        return optional.map(accountMapper::toAccountInfo).orElseThrow(AccountNotFoundException::new);
    }

    public AccountInfo getAccountByPhoneNumber(String phoneNumber) {
        Optional<Account> optional = service.getAccountByPhoneNumber(phoneNumber);
        return optional.map(accountMapper::toAccountInfo).orElseThrow(AccountNotFoundException::new);
    }

    public AccountInfo updateToken(String value, AccountField field, TokenInfo tokenInfo) {
        Optional<Account> optional = Optional.empty();
        switch (field) {
            case EMAIL -> optional = service.getAccountByEmail(value);
            case PHONE_NUMBER -> optional = service.getAccountByPhoneNumber(value);
        }

        if (optional.isPresent()) {
            Account account = optional.get();
            Token token = tokenMapper.toToken(tokenInfo);
            token.setCreationDate(LocalDateTime.now());
            updateToken(account, token);
            Optional<Account> optionalUpdated = service.save(account);
            return optionalUpdated.map(accountMapper::toAccountInfo).orElseThrow(UpdateAccountException::new);
        } else {
            throw new AccountNotFoundException();
        }
    }

    public void deleteAccount(String value, AccountField field) {
        Optional<Account> optional = Optional.empty();
        switch (field) {
            case EMAIL -> optional = service.getAccountByEmail(value);
            case PHONE_NUMBER -> optional = service.getAccountByPhoneNumber(value);
        }

        if (optional.isPresent()) {
            Account account = optional.get();
            service.delete(account);
        } else {
            throw new AccountNotFoundException();
        }
    }

    private void updateToken(Account account, Token token) {
        Set<Token> tokenList = account.getTokens();
        Set<Token> newList = new HashSet<>();
        newList.add(token);

        if (tokenList == null) {
            account.setTokens(newList);
        } else {
            newList.addAll(tokenList);
            account.setTokens(newList);
        }
    }
}