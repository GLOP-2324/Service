package com.shoploc.shoploc.domain.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById (@PathVariable Long id) {
        if (productService.getById(id) != null) {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if(products != null ) {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = productService.deleteById(id);
        if(deleted)
        return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable Long id, @RequestBody Product product) {
        Product productToUpdate = productService.updateProduct(id, product);
        if(productToUpdate!=null) {
            return new ResponseEntity<>(productToUpdate,HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }
}
