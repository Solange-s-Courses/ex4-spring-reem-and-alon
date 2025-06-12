package com.example.ex4.controller;

import com.example.ex4.constants.PlanPackageTypes;
import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.PlanPackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/package-plan")
public class PlanPackageController {

    @Autowired
    private PlanPackageService service;

 /*   @PostMapping("/category")
    public String getPlanC(@RequestBody String category) {
        service.getAllPackagesByCategory(category,  "");
    }*/

    @PostMapping(value="/add-package", consumes = "application/json")
    public ResponseEntity<?> addPackage(@RequestBody PlanPackageDTO planPackage, Principal principal) {
        System.out.println("heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        service.saveNewPackage(principal.getName(), planPackage);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }

}
