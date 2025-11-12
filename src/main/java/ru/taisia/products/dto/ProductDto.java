package ru.taisia.products.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String createdBy;
    private LocalDateTime creationDate;
    private String modifiedBy;
    private LocalDateTime modificationDate;
    private String name;
    private int count;
}
