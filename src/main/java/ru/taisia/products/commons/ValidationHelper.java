package ru.taisia.products.commons;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.taisia.products.exception.ValidateException;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationHelper {
    private final Validator validator;

    public void validate(Object object) {
        var violations = validator.validate(object);
        if (!violations.isEmpty()) {
            var msg = violations.stream()
                    .map(c -> c.getPropertyPath() + " " + c.getMessage())
                    .collect(Collectors.joining("; "));
            throw new ValidateException(msg);
        }
    }
}
