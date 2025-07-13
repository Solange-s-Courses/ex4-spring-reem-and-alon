//package com.example.ex4.components;
//
//import com.example.ex4.entity.Period;
//import com.example.ex4.entity.PlanPackage;
//import com.example.ex4.entity.PlanPackageOption;
//import com.example.ex4.entity.ProviderProfile;
//import com.example.ex4.repository.PeriodRepository;
//import com.example.ex4.repository.PlanPackageRepository;
//import com.example.ex4.repository.ProviderProfileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class TvPackagesInitializer implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private PlanPackageRepository planPackageRepository;
//
//    @Autowired
//    private PeriodRepository periodRepository;
//
//    @Autowired
//    private ProviderProfileRepository providerProfileRepository;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        List<String> companyNames = List.of("Netlify", "SkyWave", "VisionBox", "PrimeCast", "SuperTv");
//
//        // שמות חבילות
//        List<String> titles = List.of(
//                "Ultimate Sports Pack",
//                "Kids’ Adventure Bundle",
//                "Family Essentials Package",
//                "Ultimate Movies & Series Pack",
//                "News & Info Plus",
//                "International Variety Pack"
//        );
//
//        // תיאורי חבילות (2–3 שורות כל אחת)
//        List<String> descriptions = List.of(
//                "All basic channels plus every major sports channel in HD.\n" +
//                        "Perfect for sports fans who want live events and exclusive matches.",
//
//                "All popular kids’ channels and animated series in one place.\n" +
//                        "Safe, educational, and ad-free for children of all ages.",
//
//                "Essential news, lifestyle, movies, and local channels for the whole family.\n" +
//                        "Affordable and flexible with HD quality streaming.",
//
//                "Unlimited access to blockbusters, TV series, and on-demand content.\n" +
//                        "Premium movie channels and exclusive premieres every week.",
//
//                "24/7 live global and local news channels with expert analysis.\n" +
//                        "Includes weather, business, and finance updates.",
//
//                "Enjoy top international channels from around the world.\n" +
//                        "Includes news, sports, movies, and children’s shows in multiple languages."
//        );
//
//        // מחירים בסיסיים (לפי סדר החבילות)
//        int[] basePrices = {110, 85, 65, 95, 45, 120};
//
//        // הנחות (אחוזים) לכל חבילה: [3 חודשים, 6 חודשים, 12 חודשים]
//        int[][] discounts = {
//                {5, 10, 20},
//                {7, 12, 22},
//                {5, 10, 18},
//                {8, 13, 23},
//                {4, 9, 15},
//                {6, 12, 20}
//        };
//
//        List<String> periods = List.of("Three Month", "Half Yearly", "Yearly");
//        int[] months = {3, 6, 12};
//        LocalDate expiryDate = LocalDate.of(2025, 12, 31);
//
//        for (String companyName : companyNames) {
//            ProviderProfile provider = providerProfileRepository.findByCompanyName(companyName).orElse(null);
//            if (provider == null) {
//                System.err.println("Provider " + companyName + " not found, skipping...");
//                continue;
//            }
//
//            for (int pkgIdx = 0; pkgIdx < titles.size(); pkgIdx++) {
//                int basePrice = basePrices[pkgIdx];
//                int[] pkgDiscounts = discounts[pkgIdx];
//
//                PlanPackage planPackage = PlanPackage.builder()
//                        .title(titles.get(pkgIdx))
//                        .description(descriptions.get(pkgIdx))
//                        .monthlyCost(BigDecimal.valueOf(basePrice))
//                        .expiryDate(expiryDate)
//                        .providerProfile(provider)
//                        .planOptions(new ArrayList<>())
//                        .build();
//
//                for (int pIdx = 0; pIdx < periods.size(); pIdx++) {
//                    Period period = periodRepository.findByName(periods.get(pIdx)).orElse(null);
//                    if (period == null) continue;
//
//                    BigDecimal fullPrice = BigDecimal.valueOf(basePrice * months[pIdx]);
//                    BigDecimal discount = BigDecimal.valueOf(pkgDiscounts[pIdx]);
//                    BigDecimal priceWithDiscount = fullPrice.subtract(fullPrice.multiply(discount).divide(BigDecimal.valueOf(100)));
//
//                    PlanPackageOption option = PlanPackageOption.builder()
//                            .period(period)
//                            .planPackage(planPackage)
//                            .optionPrice(priceWithDiscount)
//                            .discount(discount)
//                            .build();
//
//                    planPackage.getPlanOptions().add(option);
//                }
//                planPackageRepository.save(planPackage);
//            }
//        }
//    }
//}
//
