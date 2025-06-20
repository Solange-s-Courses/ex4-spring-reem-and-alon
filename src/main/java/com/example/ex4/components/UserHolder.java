package com.example.ex4.components;

import com.example.ex4.entity.AppUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

@Component
@RequestScope
@NoArgsConstructor
public class UserHolder implements Serializable {

    private AppUser user;

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
}
