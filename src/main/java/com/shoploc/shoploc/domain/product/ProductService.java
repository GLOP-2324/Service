package com.shoploc.shoploc.domain.product;

import com.shoploc.shoploc.domain.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Product getById(Long id);

    Product createProduct (String name, String description, Double price);

    Product updateProduct (Long id, Product product);

    boolean deleteById (Long id);

}
