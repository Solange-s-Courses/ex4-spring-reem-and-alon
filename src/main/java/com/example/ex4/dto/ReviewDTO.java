/*
package com.example.ex4.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "star ranking is required")
    @Min(value = 1, message = "star ranking must be at least 1")
    @Max(value = 5, message = "star ranking must be at most 5")
    private int stars;

    private long providerId;

}
*/
