package com.zircomstadia.zircon.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaystackConfig {
    @Value("${app.SecretKey}")
    private String paystackSecretKey;

    @Bean
    public String paystackSecretKey() {
        return paystackSecretKey;
    }
}
