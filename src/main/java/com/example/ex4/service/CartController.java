//package com.example.ex4.service;
//
//import com.example.ex4.components.ShoppingCart;
//import com.example.ex4.repository.PlanPackageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/cart")
//public class CartController {
//
//    @Autowired
//    private ShoppingCart shoppingCart;
//
//    @Autowired
//    private PlanPackageRepository planPackageRepository;
//
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//    @Autowired
//    private AppUserRepository userRepository;
//
//    @Autowired
//    private ShoppingCartEntityRepository shoppingCartEntityRepository;
//
//    @PostMapping("/add")
//    public String addToCart(@RequestParam("packageId") Long packageId, Principal principal) {
//        PlanPackage pkg = planPackageRepository.findById(packageId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid package"));
//
//        shoppingCart.addItem(pkg);
//        persistSessionCart(principal);
//        return "redirect:/cart/view";
//    }
//
//    @PostMapping("/remove")
//    public String removeFromCart(@RequestParam("packageId") Long packageId, Principal principal) {
//        PlanPackage pkg = planPackageRepository.findById(packageId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid package"));
//
//        shoppingCart.removeItem(pkg);
//        persistSessionCart(principal);
//        return "redirect:/cart/view";
//    }
//
//    @GetMapping("/view")
//    public String viewCart(Model model) {
//        model.addAttribute("cart", shoppingCart);
//        return "cart/view";
//    }
//
//    @PostMapping("/clear")
//    public String clearCart(Principal principal) {
//        shoppingCart.clear();
//        persistSessionCart(principal);
//        return "redirect:/cart/view";
//    }
//
//    @PostMapping("/checkout")
//    public String checkout(Principal principal) {
//        AppUser user = userRepository.findByUsername(principal.getName());
//
//        for (PlanPackage pkg : shoppingCart.getItems()) {
//            Subscription sub = new Subscription();
//            sub.setUser(user);
//            sub.setPlanPackage(pkg);
//            subscriptionRepository.save(sub);
//        }
//
//        shoppingCart.clear();
//        persistSessionCart(principal);
//        return "redirect:/success";
//    }
//
//    private void persistSessionCart(Principal principal) {
//        if (principal != null) {
//            AppUser user = userRepository.findByUsername(principal.getName());
//
//            ShoppingCartEntity entity = new ShoppingCartEntity();
//            entity.setUser(user);
//            entity.setPackages(new ArrayList<>(shoppingCart.getItems()));
//
//            shoppingCartEntityRepository.save(entity);
//        }
//    }
//}
