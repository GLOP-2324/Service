package com.shoploc.shoploc.domain.product;

import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreService;
import com.shoploc.shoploc.domain.type.TypeProduct;
import com.shoploc.shoploc.domain.type.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private TypeProductService typeProductService;
    private StoreService storeService;

    @Autowired
    public ProductController(ProductService productService, TypeProductService typeProductService, StoreService storeService) {
        this.productService = productService;
        this.typeProductService = typeProductService;
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        if (productService.getById(id) != null) {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allProducts/{id}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable int id) {
        List<Product> products = productService.getAllProducts(id);
        if (products != null) {
            return new ResponseEntity<>(productService.getAllProducts(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/")
    public ResponseEntity<Product> createProduct(
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam("libelle") String libelle,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("type") Integer typeId,
            @RequestParam("store") Long storeId,
            @RequestParam("points") Integer points,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam("benefitsActivated") Boolean benefitsActivated,
            @RequestParam("stock") Integer stock

    ) throws IOException {
        Product product = new Product();
        product.setLibelle(libelle);
        product.setDescription(description);
        product.setPrice(price);
        if (image != null) {
            String base64Image = convertToBase64(image);
            product.setImage(base64Image);
        }
        TypeProduct type = typeProductService.getById(typeId);
        Store store = storeService.getById(storeId);
        product.setType(type);
        product.setStore(store);
        product.setPoints(points);
        product.setBenefitsActivated(benefitsActivated);
        product.setStock(stock);

        if (id == null) {
            Product createdProduct = productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } else {
            Product updatedProduct = productService.updateProduct(Long.valueOf(id), product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = productService.deleteById(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product productToUpdate = productService.updateProduct(id, product);
        if (productToUpdate != null) {
            return new ResponseEntity<>(productToUpdate, HttpStatus.OK);
        } else return ResponseEntity.notFound().build();
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}
