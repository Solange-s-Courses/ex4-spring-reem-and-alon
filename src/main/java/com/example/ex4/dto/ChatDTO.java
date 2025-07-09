package com.example.ex4.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDTO {

    private Long chatId;

    private String partnerName;

    private LocalDateTime sentAt;

    private Integer unreadCount;
}
