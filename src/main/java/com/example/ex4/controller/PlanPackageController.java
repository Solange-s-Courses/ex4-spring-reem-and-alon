package com.example.ex4.controller;

import com.example.ex4.constants.PlanPackageTypes;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.PlanPackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add-package")
    public String addPackage(@Valid @RequestBody PlanPackage planPackage, BindingResult result,Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planPackageTypes", PlanPackageTypes.values());
            return "admin/add-package-form";
        }
        service.saveNewPackage(planPackage);
        return "redirect:/admin";
    }

/*    @GetMapping("")
    public List<PlanPackage> getPlanPackages() {

    }
    */
}
