package ru.taisia.products.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.taisia.products.commons.XssSanitizer;
import ru.taisia.products.validation.XssSafe;

/**
 * Валидатор для проверки строк на наличие XSS атак.
 */
@Component
@RequiredArgsConstructor
public class XssSafeValidator implements ConstraintValidator<XssSafe, String> {

    private final XssSanitizer xssSanitizer;

    @Override
    public void initialize(XssSafe constraintAnnotation) {
        // Инициализация не требуется
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Пустые значения считаются валидными
        }
        
        // Проверяем, содержит ли строка потенциально опасный контент
        return !xssSanitizer.containsXss(value);
    }
}

