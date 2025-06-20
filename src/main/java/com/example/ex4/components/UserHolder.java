package com.example.ex4.components;

import com.example.ex4.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    private AppUser user;

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
}
