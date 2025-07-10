package com.example.ex4.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PropertyCheck {
    @Value("${server.servlet.session.timeout:NOT_FOUND}")
    private String timeout;

    @PostConstruct
    public void printTimeout() {
        System.out.println("---- timeout property: " + timeout);
    }
}
