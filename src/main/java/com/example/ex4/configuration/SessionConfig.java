package com.example.ex4.configuration;

import com.example.ex4.components.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


@Configuration
public class SessionConfig {

    @Bean
    @SessionScope
    public ShoppingCart sessionBeanCart() {
        return new ShoppingCart();
    }
}


