package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageDTO {
    Long conversationId;

    Long senderId;

    @NotBlank(message = "content is required")
    String content;

    LocalDateTime sentAt;
}
