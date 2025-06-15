//package com.example.ex4.entity;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Cart implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany
//    private List<PlanPackage> packages = new ArrayList<>();
//
//    @OneToOne
//    private AppUser user;
//
//    public Cart() {}
//
//    public List<PlanPackage> getPackages() {
//        return packages;
//    }
//
//    public void setPackages(List<PlanPackage> packages) {
//        this.packages = packages;
//    }
//
//    public AppUser getUser() {
//        return user;
//    }
//
//    public void setUser(AppUser user) {
//        this.user = user;
//    }
//
//    public void addPackage(PlanPackage item) {
//        packages.add(item);
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
