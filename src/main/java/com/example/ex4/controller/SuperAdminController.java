package com.example.ex4.controller;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.ProviderProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {

    @Autowired
    private ProviderProfileService providerProfileService;

    @GetMapping
    public String landingPage(Model model) {
        List<ProviderProfile> pendingProfiles = providerProfileService.findAllPendingProfiles();
        model.addAttribute("pendingProfiles", pendingProfiles);
        return "super_admin/index";
    }

    @PostMapping("/approve/{id}")
    public String approveProfile(@PathVariable long id, RedirectAttributes redirectAttributes) {
        providerProfileService.activateAdminAccount(id);
        redirectAttributes.addFlashAttribute("message", "Profile approved.");
        return "redirect:/super-admin";
    }

    @PostMapping("/reject/{id}")
    public String rejectProfile(@PathVariable long id, RedirectAttributes redirectAttributes) {
        providerProfileService.removeProviderProfile(id);
        redirectAttributes.addFlashAttribute("message", "Profile rejected and removed.");
        return "redirect:/super-admin";
    }
}
