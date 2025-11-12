package ru.taisia.products.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.taisia.products.validation.validator.XssSafeValidator;

import java.lang.annotation.*;

/**
 * Аннотация для валидации, что строка не содержит XSS атак.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XssSafeValidator.class)
@Documented
public @interface XssSafe {
    String message() default "Поле содержит потенциально опасный контент (XSS)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

