package com.ml.accountservice.mapper;

import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountInfo toAccountInfo(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    Account toAccount(AccountInfo accountInfo);
}