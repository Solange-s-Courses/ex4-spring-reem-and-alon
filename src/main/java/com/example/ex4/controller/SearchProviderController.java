package com.example.ex4.controller;

import com.example.ex4.components.SearchCategoryHolder;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.ProviderCategoryService;
import com.example.ex4.service.SearchProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for searching providers and categories, and showing grouped results.
 *
 * @see ProviderCategoryService
 * @see SearchProviderService
 * @see ShoppingCart
 */
@Controller
@RequestMapping("/user/search-providers")
public class SearchProviderController {

    /**
     * Service for provider category operations.
     */
    @Autowired
    private ProviderCategoryService providerCategoryService;

    /**
     * Shopping cart for the current session.
     */
    @Autowired
    private ShoppingCart sessionCart;

    /**
     * Holds the selected category for the search session.
     */
    @Autowired
    private SearchCategoryHolder searchCategoryHolder;

    /**
     * Service for searching providers.
     */
    @Autowired
    private SearchProviderService searchProviderService;

    /**
     * Initializes the search form and displays available provider categories.
     * If a category is selected, forwards to the results.
     *
     * @param category optional provider category name to filter
     * @param model Spring ModelMap for adding attributes
     * @return the search form view, or forward to the results view if category is chosen
     * @see #groupedByProviderResult(ModelMap)
     */
    @GetMapping
    public ModelAndView initSearchFormData(@RequestParam(value = "category", required = false) String category,
                                           ModelMap model) {
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        model.addAttribute("cartItems", sessionCart.getItems());

        if (category != null && !category.isBlank()) {
            searchCategoryHolder.setCategory(category);
            model.addAttribute("selectedCategory", category);
            return new ModelAndView("forward:/user/search-providers/result", model);
        }

        return new ModelAndView("user/search-providers", model);
    }

    /**
     * Shows providers grouped by the last selected category.
     *
     * @param model Spring ModelMap for adding attributes
     * @return the results view grouped by provider
     * @see SearchProviderService
     * @see SearchCategoryHolder
     * {@code Example: forwarding to results after category chosen}
     * */
    @GetMapping("/result")
    public ModelAndView groupedByProviderResult(ModelMap model) {
        String category = searchCategoryHolder.getCategory();
        model.addAttribute("results", searchProviderService.findAllProviderResultByCategory(category));
        return new ModelAndView("user/search-providers", model);
    }
}
