package com.example.ex4.components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestCartAction {
    private String providerCategory;
    private String successMessage;
    private String errorMessage;

    // Getters and Setters
    public String getProviderCategory() {
        return providerCategory;
    }

    public void setProviderCategory(String providerCategory) {
        this.providerCategory = providerCategory;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // מתודה לניקוי הודעות
    public void clearMessages() {
        this.successMessage = null;
        this.errorMessage = null;
    }
}
