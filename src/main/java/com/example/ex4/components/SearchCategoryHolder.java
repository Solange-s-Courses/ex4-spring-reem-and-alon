package com.example.ex4.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import org.springframework.web.context.annotation.RequestScope;

@Setter
@Getter
@Component
@RequestScope
public class SearchCategoryHolder {
    private String category;

}
