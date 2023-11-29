package com.ml.accountservice.mapper;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {


    public AccountInfo toAccountInfo(Account account) {
        return new AccountInfo()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setEmail(account.getEmail())
                .setPhoneNumber(account.getPhoneNumber())
                .setCreationDate(account.getCreationDate());
    }

    public Account toAccount(AccountInfo accountInfo) {
        return new Account()
                .setFirstName(accountInfo.getFirstName())
                .setLastName(accountInfo.getLastName())
                .setEmail(accountInfo.getEmail())
                .setPhoneNumber(accountInfo.getPhoneNumber())
                .setCreationDate(accountInfo.getCreationDate());
    }
}