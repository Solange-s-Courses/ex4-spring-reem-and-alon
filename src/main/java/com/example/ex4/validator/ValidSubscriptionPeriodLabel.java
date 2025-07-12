package com.example.ex4.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SubscriptionPeriodLabelValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSubscriptionPeriodLabel {
    String message() default "Invalid subscription period label";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
