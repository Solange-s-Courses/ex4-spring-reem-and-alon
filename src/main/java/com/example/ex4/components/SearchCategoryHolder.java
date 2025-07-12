package com.example.ex4.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Holds the selected search category for the current HTTP request.
 * <p>
 * This {@code @RequestScope} bean is used after the user selects a category to search.
 * For example, it allows storing the category if the user navigates to the review page and then wants to return.
 * <p>
 * Each HTTP request gets its own instance of this bean.
 */
@Setter
@Getter
@Component
public class SearchCategoryHolder {
    /**
     * The selected category for search.
     */
    private String category;
}
