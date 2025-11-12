package ru.taisia.products.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.taisia.products.dto.ProductDto;
import ru.taisia.products.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    /**
     * Преобразует модель Product в DTO.
     * Все поля включая поля аудита копируются.
     */
    ProductDto toDto(Product model);

    /**
     * Преобразует DTO в модель Product.
     * Поля аудита (createdBy, creationDate, modifiedBy, modificationDate) игнорируются,
     * так как они автоматически заполняются Spring Data Auditing.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Product toModel(ProductDto dto);
}
