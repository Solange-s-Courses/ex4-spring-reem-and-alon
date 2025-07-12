package com.example.ex4.constants;

/**
 * Represents the possible subscription periods for a package.
 * <p>
 * Each period has a duration (in months) and a human-readable label.
 */
public enum SubscriptionPeriod {
    /**
     * Three months subscription period.
     */
    QUARTERLY(3, "Three Months"),

    /**
     * Six months (half-year) subscription period.
     */
    SEMI_ANNUAL(6, "Half year"),

    /**
     * Twelve months (annual) subscription period.
     */
    ANNUAL(12, "Year");

    /**
     * The display label for the subscription period.
     */
    private final String label;

    /**
     * Constructs a subscription period.
     *
     * @param months the length of the period in months
     * @param label the display label for this period
     */
    SubscriptionPeriod(int months, String label) {
        this.label = label;
    }

    /**
     * Returns the display label for the subscription period.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
