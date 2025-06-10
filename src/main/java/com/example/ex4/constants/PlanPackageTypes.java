package com.example.ex4.constants;

public enum PlanPackageTypes {
    WEEKLY_MONTH("once a week per month"),
    ONE_TIME("one time"),
    CUSTOM("custom");

    private final String displayName;
    PlanPackageTypes(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
