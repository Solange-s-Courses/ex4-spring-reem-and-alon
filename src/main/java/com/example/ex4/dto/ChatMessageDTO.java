package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;

/**
 * Data Transfer Object representing a chat message between users.
 */
@Data
@Builder
public class ChatMessageDTO {
    /**
     * The ID of the chat (conversation) this message belongs to.
     */
    @NotNull(message = "cannot find chat id")
    private Long chatId;

    /**
     * Unique identifier for this message.
     */
    private Long messageId;

    /**
     * The message content.
     */
    @NotBlank(message = "content is required")
    private String content;

    /**
     * Timestamp of when the message was sent.
     */
    private LocalDateTime sentAt;

    /**
     * The ID of the user who sent the message.
     */
    @NotNull(message = "cannot find the user sent the message")
    private Long senderId;

    /**
     * Indicates if the message has been read.
     */
    private Boolean isRead;
}
