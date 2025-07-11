package com.example.ex4.components;

import com.example.ex4.entity.ProviderProfile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class CheckoutProviders {
    private List<ProviderProfile> providers = new ArrayList<>();

    public List<ProviderProfile> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderProfile> providers) {
        this.providers = providers;
    }
}

