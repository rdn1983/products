package ru.taisia.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.taisia.products.commons.ValidationHelper;
import ru.taisia.products.model.Product;
import ru.taisia.products.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ValidationHelper validationHelper;

    public List<Product> findALl() {
        List<Product> list = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            list.add(p);
        }
        return list;
    }

    public Product create(Product product) {
        validationHelper.validate(product);

        // Устанавливаем ID для новой сущности
        // Поля аудита (createdBy, creationDate, modifiedBy, modificationDate) 
        // будут автоматически заполнены Spring Data Auditing при сохранении
        product.setId(UUID.randomUUID());
        product.setNew(true);

        // Spring Data JDBC автоматически заполнит поля аудита через @EnableJdbcAuditing
        return productRepository.save(product);
    }
}
