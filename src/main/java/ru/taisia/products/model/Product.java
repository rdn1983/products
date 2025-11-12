package ru.taisia.products.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.taisia.products.validation.XssSafe;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("PRODUCTS")
public class Product implements Persistable<UUID> {
    @Id
    @Column("ID")
    private UUID id;
    
    @XssSafe
    @Size(max = 255, message = "Имя создателя не должно превышать 255 символов")
    @CreatedBy
    @Column("CREATED_BY")
    private String createdBy;
    
    @CreatedDate
    @Column("CREATED_AT")
    private LocalDateTime creationDate;
    
    @XssSafe
    @Size(max = 255, message = "Имя редактора не должно превышать 255 символов")
    @LastModifiedBy
    @Column("MODIFIED_BY")
    private String modifiedBy;
    
    @LastModifiedDate
    @Column("MODIFIED_AT")
    private LocalDateTime modificationDate;
    
    @NotBlank(message = "Название продукта обязательно")
    @NotNull(message = "Название продукта обязательно")
    @XssSafe
    @Size(min = 1, max = 500, message = "Название продукта должно содержать от 1 до 500 символов")
    @Column("NAME")
    private String name;
    
    @Min(value = 0, message = "Количество не может быть отрицательным")
    @Column("COUNT")
    private int count;

    @Transient
    private boolean isNew = true; // По умолчанию true для новых сущностей

    @Override
    public boolean isNew() {
        // Сущность считается новой, если id == null или isNew == true
        return id == null || isNew;
    }
}
