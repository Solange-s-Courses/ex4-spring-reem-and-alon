package com.example.ex4.entity;

import jakarta.persistence.*;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private PlanPackage planPackage;

    @ManyToOne
    private AppUser appUser;

    public Subscription(){}

    public void setPlanPackage(PlanPackage planPackage) { this.planPackage = planPackage; }
    public void setAppUser(AppUser appUser) { this.appUser = appUser; }
    public long getId() { return id; }
    public PlanPackage getPlanPackage() { return planPackage; }
    public AppUser getAppUser() { return appUser; }
}
