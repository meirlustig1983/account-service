package com.ml.accountservice.controller;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountManager manager;

    @Autowired
    public AccountController(AccountManager manager) {
        this.manager = manager;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AccountInfo>> getAll() {
        List<AccountInfo> accounts = manager.getAll();
        if (accounts != null) {
            return ResponseEntity.ok(accounts);
        } else {
            throw new IllegalStateException("failed to get all accounts data");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountInfo> createAccount(@RequestBody AccountInfo accountInfo) {

        AccountInfo updatedAccountInfo = manager.createAccount(accountInfo);
        if (updatedAccountInfo != null) {
            return ResponseEntity.ok(updatedAccountInfo);
        } else {
            throw new IllegalStateException("failed to save account data");
        }
    }
}
