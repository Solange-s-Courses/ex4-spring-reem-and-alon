package com.example.ex4.components;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class SessionDebugListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("== SESSION CREATED ==");
        System.out.println("Max inactive interval: " + se.getSession().getMaxInactiveInterval());
    }
}
