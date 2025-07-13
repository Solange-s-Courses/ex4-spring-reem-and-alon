package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a chat (conversation) between a client and a provider.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Chat implements Serializable {

    /**
     * Unique identifier for the chat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * The client (user) participating in the chat.
     *
     * @see User
     */
    @ManyToOne
    private User client;

    /**
     * The provider profile participating in the chat.
     *
     * @see ProviderProfile
     */
    @ManyToOne
    private ProviderProfile provider;

    /**
     * List of messages in this chat.
     *
     * @see Message
     */
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    /**
     * Date and time when the chat was created.
     */
    private LocalDateTime createdAt;

    /**
     * The number of unread messages in this chat (not persisted).
     */
    @Transient
    private long unreadCount;
}
