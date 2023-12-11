package com.shoploc.shoploc.domain.product;


import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.domain.type.TypeProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private StoreRepository storeRepository;
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
    public Product createProduct(String libelle, String description, Double price, TypeProduct type, File image, Store store) {
        Product product = new Product();
        product.setLibelle(libelle);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        product.setType(type);
        Store storeActual = storeRepository.findById(store.getId()).orElse(null);
        product.setStore(storeActual);
        storeActual.setProducts(product);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setLibelle(product.getLibelle());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImage(product.getImage());
            existingProduct.setStore(product.getStore());
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