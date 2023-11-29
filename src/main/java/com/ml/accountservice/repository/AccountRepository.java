package com.ml.accountservice.repository;

import com.ml.accountservice.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);
}
