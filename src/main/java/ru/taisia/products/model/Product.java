package ru.taisia.products.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("PRODUCTS")
public class Product implements Persistable<UUID> {
    @Id
    @Column("ID")
    private UUID id;
    @CreatedBy
    @Column("CREATED_BY")
    private String createdBy;
    @CreatedDate
    @Column("CREATED_AT")
    private LocalDateTime creationDate;
    @LastModifiedBy
    @Column("MODIFIED_BY")
    private String modifiedBy;
    @LastModifiedDate
    @Column("MODIFIED_AT")
    private LocalDateTime modificationDate;
    @NotNull
    @Column("NAME")
    private String name;
    @Column("COUNT")
    private int count;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
