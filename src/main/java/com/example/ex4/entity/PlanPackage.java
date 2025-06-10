package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Entity
public class PlanPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Please enter a valid number")
    private String price;


    @ManyToOne
    //@JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

    @NotBlank(message= "this field is required")
    private String packageType;

    public PlanPackage(){}
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getPackageType() { return packageType; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
    public void setPackageType(String packageType) { this.packageType = packageType; }
    public void setAppUser(AppUser appUser) { this.appUser = appUser; }
}
