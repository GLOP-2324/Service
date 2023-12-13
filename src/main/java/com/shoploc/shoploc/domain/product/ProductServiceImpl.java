package com.shoploc.shoploc.domain.product;


import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.domain.type.TypeProduct;
import com.shoploc.shoploc.domain.type.TypeProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private TypeProductRepository typeProductRepository;
    private ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, StoreRepository storeRepository){
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional
    public List<Product> getAllProducts(Integer idStore) {
        return (List<Product>) productRepository.findByStoreId(idStore);
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
            return productRepository.findById(id).get();
        else return null;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        Product newProduct = new Product();
        newProduct.setLibelle(product.getLibelle());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setImage(product.getImage());
        newProduct.setStore(product.getStore());
        newProduct.setType(product.getType());

        productRepository.save(product);
        return product;
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