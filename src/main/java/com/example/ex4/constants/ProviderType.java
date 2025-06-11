package com.example.ex4.constants;

public enum ProviderType {
    INTERNET("Internet"),
    CELLULAR("Cellular");

    private final String displayName;
    ProviderType(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}




