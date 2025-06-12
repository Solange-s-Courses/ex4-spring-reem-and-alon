package com.example.ex4.controller;

import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/package-plan")
public class PlanPackageController {

    @Autowired
    private PlanPackageService service;

    @PostMapping("/category")
    public String getPlanC(@RequestBody String category) {
        service.getAllPackagesByCategory(category,  "");

    }
}
