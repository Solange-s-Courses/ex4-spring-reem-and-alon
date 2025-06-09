package com.example.ex4.constants;

public enum JobType {
    HOUSE_CLEANING("House Cleaning"),
    GARDENER("Gardener"),
    HANDYMAN("Handyman");

    private final String displayName;
    JobType(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}




