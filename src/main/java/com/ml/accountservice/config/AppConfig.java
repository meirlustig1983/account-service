package com.ml.accountservice.config;

import com.ml.accountservice.mapper.AccountMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AccountMapper accountMapper() {
        return AccountMapper.INSTANCE;
    }
}