package com.example.ex4.constants;

/**
 * A utility class that holds constant error message strings used across the application.
 * These messages are used to provide consistent and centralized error handling feedback
 * to users and developers.
 */
public class ErrorMsg {

    /** Message when the user's cart is empty during checkout */
    public static final String EMPTY_CART = "You dont have any items in your cart!";

    /** Message when attempting to purchase a duplicate subscription from the same package */
    public static final String DUPLICATE_SUBSCRIPTION_PURCHASE = "You’ve already purchased a subscription from this package. You can’t buy another one!";

    /** Message when already subscribed to some of the selected packages */
    public static final String ALREADY_SUBSCRIBED = "Already subscribed for some of the packages!";

    /** Message when user doesn't have enough credit to complete the yearly purchase */
    public static final String NOT_ENOUGH_CREDIT = "Not enough credit balance for a yearly commitment for all packages";

    /** Message when a chat message cannot be found */
    public static final String MSG_NOT_FOUND = "message not found";

    /** Message when the user is not allowed to mark a message as read */
    public static final String ERR_READ_MSG = "read message not allowed";

    /** Message when no provider profile is found for the current user */
    public static final String PROFILE_NOT_FOUND = "No provider profile found";

    /** Message when the selected plan package is no longer available */
    public static final String PLAN_PACKAGE_NOT_AVAILABLE = "Cannot subscribe service plan package. because its not available anymore.";

    /** Message when a specific plan package option is not found */
    public static final String PLAN_OPTION_NOT_FOUND = "Plan package option not found";

    /** Message if product already exists in cart */
    public static final String PRODUCT_ALREADY_EXISTS = "Product already exists in the cart!";

    /** Message if user already subscribed to product */
    public static final String ITEM_ALREADY_SUBSCRIBED = "Already subscribed to that item";

    /** Message if product does not exist in cart */
    public static final String PRODUCT_NOT_EXISTS = "Product does not exists in the cart!";

    /** Message for invalid request */
    public static final String INVALID_REQUEST = "Invalid request: ";

    /** Message for category of the provider not found */
    public static final String PROVIDER_CATEGORY_NOT_FOUND = "Provider category not found";
}