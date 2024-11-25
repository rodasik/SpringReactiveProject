package com.rodasik.springex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
    @Bean
    @Primary
    public Validator springValidator() { return new LocalValidatorFactoryBean(); }
}
