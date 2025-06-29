package com.example.ex4.components;

import org.springframework.stereotype.Component;

@Component
public class RequestUrlHolder {
    private String url;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
