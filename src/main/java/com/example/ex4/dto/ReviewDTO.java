package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ReviewDTO {

    @NotBlank(message = "text is required")
    private String reviewText;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "star ranking is required")
    private double stars;

}
