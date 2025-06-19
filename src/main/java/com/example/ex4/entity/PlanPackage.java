package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message= "this field is required")
    private String packageType;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Please enter a valid number")
    private String price;


    @ManyToOne
    private ProviderProfile providerProfile;
}
