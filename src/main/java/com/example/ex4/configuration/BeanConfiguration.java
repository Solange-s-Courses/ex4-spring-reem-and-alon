package com.example.ex4.configuration;

import com.example.ex4.components.SearchCategoryHolder;
import com.example.ex4.components.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;


@Configuration
public class BeanConfiguration {

    @Bean
    @SessionScope
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

    @Bean
    @RequestScope
    public SearchCategoryHolder urlHolder() {
        return new SearchCategoryHolder();
    }

/*    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }*/
}


