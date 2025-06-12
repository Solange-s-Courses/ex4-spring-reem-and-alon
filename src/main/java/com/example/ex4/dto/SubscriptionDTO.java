package com.example.ex4.dto;

import com.example.ex4.entity.Subscription;

public class SubscriptionDTO {
    private long id;
    private String planTitle;
    private String userName;

    public SubscriptionDTO(Subscription s) {
        this.id = s.getId();
        this.planTitle = s.getPlanPackage().getTitle();
        this.userName = s.getAppUser().getUserName();
    }

    public long getId() {return id;}
    public String getPlanTitle() {return planTitle;}
    public String getUserName() {return userName;}
    public void setPlanTitle(String planTitle) {this.planTitle = planTitle;}
    public void setUserName(String userName) {this.userName = userName;}
}

