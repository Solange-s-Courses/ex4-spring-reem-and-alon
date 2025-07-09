package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.MessageService;
import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAdvice(assignableTypes = {UserController.class, SearchProviderController.class, CheckoutController.class})
public class UserControllerAdvice {

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private MessageService messageService;

    @ModelAttribute("shoppingCart")
    public List<PlanPackage> shoppingCart() {
        Set<Long> pkgIds = sessionCart.getPkgIds();
        return planPackageService.findAllProducts(pkgIds);
    }

    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute("balance")
    public int balance(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        return userPrincipal.getUser().getCreditBalance();
    }

    @ModelAttribute("unreadMessages")
    public Map<Long, Long> unreadMessages(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        return messageService.getUnreadMessagesCount(userPrincipal.getUser());
    }
}
