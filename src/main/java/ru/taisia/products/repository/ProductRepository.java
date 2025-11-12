package ru.taisia.products.repository;

import org.springframework.data.repository.CrudRepository;
import ru.taisia.products.model.Product;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
