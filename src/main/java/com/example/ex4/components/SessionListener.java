/*
package com.example.ex4.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionDestroyed(HttpSessionDestroyedEvent event) {
        System.out.println("Session expired - WebSocket will be disconnected automatically");
        messagingTemplate.convertAndSend("/topic/disconnect", "SESSION_EXPIRED");
    }
}*/
