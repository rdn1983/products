package ru.taisia.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.taisia.products.validation.XssSafe;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Имя пользователя обязательно")
    @XssSafe
    @Size(min = 1, max = 100, message = "Имя пользователя должно содержать от 1 до 100 символов")
    private String username;
    
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 1, max = 255, message = "Пароль должен содержать от 1 до 255 символов")
    private String password;
}
