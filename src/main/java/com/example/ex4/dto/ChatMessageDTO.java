package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
public class ChatMessageDTO {
    @NotNull(message = "cannot find chat id")
    private Long chatId;

    @NotBlank(message = "content is required")
    private String content;

    private LocalDateTime sentAt;

    @NotNull(message = "cannot find the user sent the message")
    private Long senderId;

    private Boolean isRead;
}

