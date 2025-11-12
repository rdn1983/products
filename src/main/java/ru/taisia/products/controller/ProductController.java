package ru.taisia.products.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.taisia.products.dto.ProductDto;
import ru.taisia.products.mapper.ProductMapper;
import ru.taisia.products.model.Product;
import ru.taisia.products.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<ProductDto> findAll() {
        return productService.findALl().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody ProductDto dto) {
        Product product = productMapper.toModel(dto);
        product = productService.create(product);
        return productMapper.toDto(product);
    }
}
