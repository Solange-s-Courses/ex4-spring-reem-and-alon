package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDTO {

    @NotBlank(message = "userName is required")
    Long conversationId;

    @NotBlank(message = "provider name is required")
    String partnerName;

    @NotBlank(message = "img is required")
    private String partnerImgUrl;
}
