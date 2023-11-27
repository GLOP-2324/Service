package com.shoploc.shoploc.service;

import com.shoploc.shoploc.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getById(Long id);

    Product createProduct (String name, String description, Double price);

    Product updateProduct (Long id, String name, String description, Double price);

    void deleteById (Long id);

}
