package com.ml.accountservice.controller;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.dto.AccountRequest;
import com.ml.accountservice.dto.TokenUpdateRequest;
import com.ml.accountservice.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountManager manager;

    @Autowired
    public AccountController(AccountManager manager) {
        this.manager = manager;
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountInfo> createAccount(@RequestBody AccountInfo accountInfo) {
        return ResponseEntity.ok(manager.createAccount(accountInfo));
    }

    @GetMapping
    public ResponseEntity<AccountInfo> getAccount(@RequestBody AccountRequest request) {
        AccountInfo account = null;
        switch (request.field()) {
            case EMAIL -> account = manager.getAccountByEmail(request.value());
            case PHONE_NUMBER -> account = manager.getAccountByPhoneNumber(request.value());
        }
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/update-token")
    public ResponseEntity<AccountInfo> updateToken(@RequestBody TokenUpdateRequest request) {
        return ResponseEntity.ok(manager.updateToken(request.value(), request.field(), request.tokenInfo()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountRequest request) {
        manager.deleteAccount(request.value(), request.field());
        return ResponseEntity.ok().build();
    }
}