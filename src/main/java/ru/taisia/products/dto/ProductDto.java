package ru.taisia.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.taisia.products.validation.XssSafe;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    
    @XssSafe
    @Size(max = 255, message = "Имя создателя не должно превышать 255 символов")
    private String createdBy;
    
    private LocalDateTime creationDate;
    
    @XssSafe
    @Size(max = 255, message = "Имя редактора не должно превышать 255 символов")
    private String modifiedBy;
    
    private LocalDateTime modificationDate;
    
    @NotBlank(message = "Название продукта обязательно")
    @XssSafe
    @Size(min = 1, max = 500, message = "Название продукта должно содержать от 1 до 500 символов")
    private String name;
    
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private int count;
}
