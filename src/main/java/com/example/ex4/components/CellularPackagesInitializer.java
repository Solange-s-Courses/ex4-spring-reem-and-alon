//package com.example.ex4.components;
//
//import com.example.ex4.entity.PlanPackage;
//import com.example.ex4.entity.PlanPackageOption;
//import com.example.ex4.entity.Period;
//import com.example.ex4.entity.ProviderProfile;
//import com.example.ex4.repository.PlanPackageRepository;
//import com.example.ex4.repository.PeriodRepository;
//import com.example.ex4.repository.ProviderProfileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.*;
//
//@Component
//public class CellularPackagesInitializer implements ApplicationListener<ContextRefreshedEvent> {
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
//        List<String> companyNames = List.of("WaveTel", "Comovo", "CellNet");
//
//        // מחירים ואחוזי הנחה: [basePrice, [חודש, חצי שנה, שנה]]
//        Map<String, List<PlanConfig>> configs = Map.of(
//                "WaveTel", List.of(
//                        new PlanConfig(39, new int[]{2, 5, 12}),
//                        new PlanConfig(59, new int[]{3, 7, 13}),
//                        new PlanConfig(79, new int[]{0, 5, 12}),
//                        new PlanConfig(49, new int[]{2, 6, 11}),
//                        new PlanConfig(99, new int[]{3, 8, 15}),
//                        new PlanConfig(44, new int[]{2, 6, 10})
//                ),
//                "Comovo", List.of(
//                        new PlanConfig(42, new int[]{2, 4, 9}),
//                        new PlanConfig(63, new int[]{3, 8, 12}),
//                        new PlanConfig(85, new int[]{3, 7, 14}),
//                        new PlanConfig(52, new int[]{3, 5, 8}),
//                        new PlanConfig(105, new int[]{4, 9, 16}),
//                        new PlanConfig(48, new int[]{1, 5, 9})
//                ),
//                "CellNet", List.of(
//                        new PlanConfig(38, new int[]{3, 6, 15}),
//                        new PlanConfig(57, new int[]{1, 7, 14}),
//                        new PlanConfig(81, new int[]{2, 6, 16}),
//                        new PlanConfig(47, new int[]{1, 5, 12}),
//                        new PlanConfig(96, new int[]{5, 10, 17}),
//                        new PlanConfig(43, new int[]{1, 5, 9})
//                )
//        );
//
//        List<String> periods = List.of("Three Month", "Half Yearly", "Yearly");
//        int[] months = {1, 6, 12};
//
//        // שמות חבילות
//        List<String> titles = List.of(
//                "Cellular Starter",
//                "Unlimited Talk Pro",
//                "Data Freedom",
//                "Youth Connect",
//                "Traveler Global",
//                "Text & Surf"
//        );
//
//        // תיאורים בני 2–3 שורות, שונים לכל חברה ולכל חבילה
//        Map<String, List<String>> descriptions = Map.of(
//                "WaveTel", List.of(
//                        "Perfect for everyday needs: 100 min, 100 SMS, and 2GB basic mobile data.\n" +
//                                "Great for those who want reliable service at an entry price.\n" +
//                                "Stay connected with WaveTel's trusted network.",
//
//                        "Make as many calls as you wish and send 500 SMS each month.\n" +
//                                "Includes 10GB high-speed data for browsing and apps.\n" +
//                                "Best value for talkative users who want total freedom.",
//
//                        "Enjoy 300 min, 300 SMS, and truly unlimited 5G data (first 100GB fast).\n" +
//                                "Perfect for streaming, gaming, and working from anywhere.\n" +
//                                "Never worry about running out of data with WaveTel.",
//
//                        "Specially made for young adults and students.\n" +
//                                "400 min, 1000 SMS, and 30GB blazing-fast 4G+ data.\n" +
//                                "Everything you need for chat, study, and fun!",
//
//                        "Go global: Unlimited Israel calls, 200 minutes worldwide, 5GB roaming.\n" +
//                                "500 SMS included for total flexibility.\n" +
//                                "WaveTel connects you, wherever you go.",
//
//                        "For texters and surfers: 200 min, 2000 SMS, 15GB of fast mobile data.\n" +
//                                "Best for those who love messaging and occasional browsing.\n" +
//                                "Keep your chats and web access always on."
//                ),
//                "Comovo", List.of(
//                        "All the basics: 100 min, 100 SMS, 2GB of mobile data at a great price.\n" +
//                                "Comovo ensures stable connectivity for everyday communication.\n" +
//                                "Best for new users and children.",
//
//                        "Talk as much as you want, send 500 SMS, plus 10GB fast internet.\n" +
//                                "Never miss a moment—perfect for active families.\n" +
//                                "All with Comovo’s friendly support.",
//
//                        "Unlimited 5G for binge-watchers and gamers, with 300 min and 300 SMS.\n" +
//                                "High-speed until 100GB, then unlimited slow data.\n" +
//                                "Total digital freedom from Comovo.",
//
//                        "A youth-focused plan: 400 min, 1000 SMS, and 30GB data.\n" +
//                                "Enjoy fast browsing and social media everywhere.\n" +
//                                "Stay in touch all day with Comovo.",
//
//                        "Travel anywhere—unlimited local calls, 200 intl min, 5GB global roaming.\n" +
//                                "500 SMS included for full flexibility on the road.\n" +
//                                "Roam free, pay less!",
//
//                        "Text & Surf: 200 min, 2000 SMS, 15GB for socialites and chat lovers.\n" +
//                                "Enough data for browsing and daily apps.\n" +
//                                "Comovo keeps you connected."
//                ),
//                "CellNet", List.of(
//                        "Affordable essentials: 100 min, 100 SMS, and 2GB of mobile data.\n" +
//                                "Great choice for light users and backup numbers.\n" +
//                                "Stay simple, stay connected.",
//
//                        "Unlimited calls & 500 SMS monthly, plus 10GB 4G data.\n" +
//                                "CellNet’s popular plan for families and talkers.\n" +
//                                "Freedom to call and message all day.",
//
//                        "Heavy internet user? Get unlimited 5G (100GB at full speed),\n" +
//                                "with 300 min and 300 SMS included every month.\n" +
//                                "CellNet: Your digital powerhouse.",
//
//                        "Tailored for students: 400 min, 1000 SMS, and 30GB fast data.\n" +
//                                "All you need for chats, school, and entertainment.\n" +
//                                "Connect with CellNet wherever you are.",
//
//                        "Travel with confidence: Unlimited local, 200 intl min, 5GB global data.\n" +
//                                "500 SMS to stay in touch worldwide.\n" +
//                                "CellNet keeps you close to home.",
//
//                        "If you love messaging: 200 min, 2000 SMS, and 15GB data.\n" +
//                                "Best for chatters, social media, and online fun.\n" +
//                                "Choose CellNet for your lifestyle."
//                )
//        );
//
//        LocalDate expiryDate = LocalDate.of(2025, 12, 31);
//
//        for (String companyName : companyNames) {
//            ProviderProfile provider = providerProfileRepository.findByCompanyName(companyName).orElse(null);
//            if (provider == null) {
//                System.err.println("Provider " + companyName + " not found, skipping...");
//                continue;
//            }
//
//            List<PlanConfig> plans = configs.get(companyName);
//            List<String> planDescriptions = descriptions.get(companyName);
//
//            for (int pkgIdx = 0; pkgIdx < 6; pkgIdx++) {
//                PlanConfig cfg = plans.get(pkgIdx);
//
//                PlanPackage planPackage = PlanPackage.builder()
//                        .title(titles.get(pkgIdx))
//                        .description(planDescriptions.get(pkgIdx))
//                        .monthlyCost(BigDecimal.valueOf(cfg.basePrice))
//                        .expiryDate(expiryDate)
//                        .providerProfile(provider)
//                        .planOptions(new ArrayList<>())
//                        .build();
//
//                for (int pIdx = 0; pIdx < periods.size(); pIdx++) {
//                    Period period = periodRepository.findByName(periods.get(pIdx)).orElse(null);
//                    if (period == null) continue;
//                    BigDecimal fullPrice = BigDecimal.valueOf(cfg.basePrice * months[pIdx]);
//                    BigDecimal discount = BigDecimal.valueOf(cfg.discounts[pIdx]);
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
//
//    private static class PlanConfig {
//        int basePrice;
//        int[] discounts;
//        public PlanConfig(int basePrice, int[] discounts) {
//            this.basePrice = basePrice;
//            this.discounts = discounts;
//        }
//    }
//}
