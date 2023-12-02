package com.ml.accountservice.mapper;

import com.ml.accountservice.dto.TokenInfo;
import com.ml.accountservice.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    @Mapping(target = "creationDate", ignore = true)
    Token toToken(TokenInfo tokenInfo);
}