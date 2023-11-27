package com.shoploc.shoploc.service;

import com.shoploc.shoploc.entity.Product;
import com.shoploc.shoploc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(String name, String description, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, String name, String description, Double price) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(name);
            existingProduct.setDescription(description);
            existingProduct.setPrice(price);
            return productRepository.save(existingProduct);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
