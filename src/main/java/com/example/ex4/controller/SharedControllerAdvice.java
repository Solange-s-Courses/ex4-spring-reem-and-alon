//package com.example.ex4.controller;
//
//import com.example.ex4.MyUserPrincipal;
//import com.example.ex4.service.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import java.security.Principal;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice(assignableTypes = ChatController.class)
//public class SharedControllerAdvice {
//
//    @Autowired
//    private MessageService messageService;
//
//    @ModelAttribute("userName")
//    public String userName(Principal principal) {
//        return principal.getName();
//    }
//
//    @ModelAttribute("unreadMessages")
//    public Map<Long, Long> unreadMessages(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
//        Map<Long, Long> unreadMessages = messageService.getUnreadMessagesCount(userPrincipal.getUser());
//        System.out.println(unreadMessages);
//        return unreadMessages;
//    }
//}
