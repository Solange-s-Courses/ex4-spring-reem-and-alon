package com.example.ex4.constants;

/**
 * Represents the types of financial transactions supported in the system.
 * <ul>
 *   <li>{@link #TRANSFER} – Money transfer between accounts or users.</li>
 *   <li>{@link #DEPOSIT} – Deposit of funds into an account.</li>
 *   <li>{@link #REFUND} – Refund transaction.</li>
 *   <li>{@link #FAILED} – Transaction charge failed.</li>
 * </ul>
 */
public enum TransactionType {
    /** Money transfer between accounts or users. */
    TRANSFER,
    /** Deposit of funds into an account. */
    DEPOSIT,
    /** Refund transaction. */
    REFUND,
    /** Transaction charge failed. */
    FAILED
}
