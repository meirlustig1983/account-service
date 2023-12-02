package com.ml.accountservice.config;

import com.ml.accountservice.mapper.AccountMapper;
import com.ml.accountservice.mapper.TokenMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AccountMapper accountMapper() {
        return AccountMapper.INSTANCE;
    }

    @Bean
    public TokenMapper tokenMapper() {
        return TokenMapper.INSTANCE;
    }
}
