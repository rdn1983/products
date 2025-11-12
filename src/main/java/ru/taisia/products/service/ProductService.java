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

        product.setId(UUID.randomUUID());
        product.setNew(true);

        return productRepository.save(product);
    }
}
