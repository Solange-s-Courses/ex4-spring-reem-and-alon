package com.example.ex4.components;

import com.example.ex4.entity.ProviderProfile;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of {@link  ProviderProfile} objects during a single HTTP request.
 * <p>
 * This component is request-scoped, so each HTTP request will have its own instance.
 * Useful for storing providers during a checkout process.
 */
@Component
public class CheckoutProviders {

    /**
     * The list of providers involved in the current checkout.
     */
    private List<ProviderProfile> providers;

    /**
     * Initializes the providers list for a new request.
     */
    public CheckoutProviders() {
        providers = new ArrayList<>();
    }

    /**
     * Gets the list of providers involved in the current checkout.
     *
     * @return list of {@link ProviderProfile}
     */
    public List<ProviderProfile> getProviders() {
        return providers;
    }

    /**
     * Sets the list of providers for the current checkout.
     *
     * @param providers the list of {@link ProviderProfile} to set
     */
    public void setProviders(List<ProviderProfile> providers) {
        this.providers = providers;
    }
}
