package com.example.ex4.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket configuration class for enabling STOMP messaging with a simple broker.
 * <p>
 * This class configures endpoints and message routing for real-time communication
 * between clients and the server.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker with application destination prefixes and simple broker endpoints.
     *
     * <ul>
     *   <li><b>enableSimpleBroker</b> – Enables a simple in-memory broker for destinations like <code>/topic</code> and <code>/queue</code>.</li>
     *   <li><b>setApplicationDestinationPrefixes</b> – Defines <code>/app</code> as the prefix for client-to-server messages (controller mappings).</li>
     * </ul>
     *
     * @param config the {@link MessageBrokerRegistry} to configure
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers STOMP endpoints for WebSocket communication.
     * <p>
     * The endpoint <code>/chat-websocket</code> is exposed, and supports SockJS fallback for clients that do not support native WebSocket.
     *
     * @param registry the {@link StompEndpointRegistry} to register endpoints with
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
