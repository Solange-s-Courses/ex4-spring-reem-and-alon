package com.example.ex4.constants;

public enum SubscriptionPeriod {
    QUARTERLY(3, "Three Months"),
    SEMI_ANNUAL(6, "Half year"),
    ANNUAL(12, "Year");

    private final int months;
    private final String label;

    SubscriptionPeriod(int months, String label) {
        this.months = months;
        this.label = label;
    }
    public int getMonths() { return months; }
    public String getLabel() { return label; }
}

