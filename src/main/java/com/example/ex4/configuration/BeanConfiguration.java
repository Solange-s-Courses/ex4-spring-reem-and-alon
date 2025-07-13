package com.example.ex4.configuration;

import com.example.ex4.components.SearchCategoryHolder;
import com.example.ex4.components.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Configuration class for custom application-scoped beans.
 * <p>
 * Defines beans with {@code @SessionScope} and {@code @RequestScope}
 * to manage user-specific and request-specific state.
 */
@Configuration
public class BeanConfiguration {

    /**
     * Defines the {@link ShoppingCart} bean as session-scoped.
     * <p>
     * Each user session will have its own shopping cart instance.
     *
     * @return a new instance of {@link ShoppingCart}
     */
    @Bean
    @SessionScope
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

    /**
     * Defines the {@link SearchCategoryHolder} bean as request-scoped.
     * <p>
     * Each HTTP request will have its own instance to hold the search category.
     *
     * @return a new instance of {@link SearchCategoryHolder}
     */
    @Bean
    @RequestScope
    public SearchCategoryHolder searchCategoryHolder() {
        return new SearchCategoryHolder();
    }

    /**
     * Defines the {@link CheckoutProviders} bean as request-scoped.
     * <p>
     * Each HTTP request will have it the providers that need to create the chat (with the user)
     *
     * @return a new instance of {@link CheckoutProviders}
     */
/*    @Bean
    @RequestScope
    public CheckoutProviders checkoutProviders() {
        return new CheckoutProviders();
    }*/
}


