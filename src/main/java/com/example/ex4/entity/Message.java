package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity representing a message within a chat conversation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message implements Serializable {

    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * The ID of the user who sent the message.
     */
    @Column(nullable = false)
    private Long senderID;

    /**
     * The content of the message.
     */
    @Column(nullable = false)
    private String content;

    /**
     * The timestamp when the message was sent.
     */
    @Column(nullable = false)
    private LocalDateTime sentAt;

    /**
     * Indicates if the message has been read.
     * Default is {@code false}.
     */
    @Builder.Default
    private boolean isRead = false;

    /**
     * The chat this message belongs to.
     *
     * @see Chat
     */
    @ManyToOne
    private Chat chat;
}
