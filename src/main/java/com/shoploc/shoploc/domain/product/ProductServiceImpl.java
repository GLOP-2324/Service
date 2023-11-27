package com.shoploc.shoploc.domain.product;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
            return productRepository.findById(id).get();
        else return null;
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
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }

        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if(productRepository.findById(id).isPresent()){
                productRepository.deleteById(id);
                return true;
        }
        else return false;
    }
}
