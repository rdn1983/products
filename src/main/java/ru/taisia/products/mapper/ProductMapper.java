package ru.taisia.products.mapper;

import org.mapstruct.Mapper;
import ru.taisia.products.dto.ProductDto;
import ru.taisia.products.model.Product;

@Mapper
public interface ProductMapper {
    ProductDto toDto(Product model);

    Product toModel(ProductDto dto);
}
