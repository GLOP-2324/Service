package com.shoploc.shoploc.controller;

import com.shoploc.shoploc.entity.Product;
import com.shoploc.shoploc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Optional<Product> getById (@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
        return createdProduct;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct (@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id,
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
