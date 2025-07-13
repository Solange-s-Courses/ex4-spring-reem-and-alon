package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

/**
 * Advice class for injecting common user-related attributes into the model,
 * available in multiple controllers (user, search, checkout, chat).
 *
 * @see ShoppingCart
 * @see MessageService
 * @see UserController
 * @see SearchProviderController
 * @see CheckoutController
 * @see ChatController
 */
@ControllerAdvice(assignableTypes = {UserController.class, SearchProviderController.class, CheckoutController.class,ChatController.class})
public class UserControllerAdvice {

    /**
     * The session-scoped shopping cart.
     */
    @Autowired
    private ShoppingCart sessionCart;

    /**
     * Service for message operations.
     */
    @Autowired
    private MessageService messageService;

    /**
     * Returns the number of items in the shopping cart.
     *
     * @return the cart size
     * @see ShoppingCart#getSize()
     */
    @ModelAttribute("shoppingCartSize")
    public int shoppingCartSize() {
        return sessionCart.getSize();
    }

    /**
     * Returns the current user's username.
     *
     * @param principal the Spring Security principal
     * @return the username
     * @see Principal#getName()
     */
    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal.getName();
    }

    /**
     * Returns the current user's credit balance.
     *
     * @param userPrincipal the authenticated user principal
     * @return the user's credit balance
     */
    @ModelAttribute("balance")
    public BigDecimal balance(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        return userPrincipal.getUser().getCreditBalance();
    }

    /**
     * Returns the total number of unread messages for the current user.
     *
     * @param userPrincipal the authenticated user principal
     * @return sum of all unread messages across chats
     */
    @ModelAttribute("unreadMessagesCount")
    public long unreadMessagesCount(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        Map<Long, Long> unreadMessages = messageService.getUnreadMessagesCount(userPrincipal.getUser());
        return unreadMessages.values().stream().mapToLong(Long::longValue).sum();
    }
}
