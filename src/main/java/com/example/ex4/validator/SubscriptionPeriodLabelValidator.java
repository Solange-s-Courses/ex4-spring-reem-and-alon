//package com.example.ex4.validator;
//
//import com.example.ex4.constants.SubscriptionPeriod;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.util.Arrays;
//
//public class SubscriptionPeriodLabelValidator implements ConstraintValidator<ValidSubscriptionPeriodLabel, String> {
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (value == null) return false;
//        return Arrays.stream(SubscriptionPeriod.values())
//                .anyMatch(period -> period.getLabel().equals(value));
//    }
//}
