package com.shoploc.shoploc.domain.product;

import com.shoploc.shoploc.domain.type.TypeProduct;
import com.shoploc.shoploc.domain.store.Store;
import jakarta.transaction.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts(Integer idStore);

    Product getById(Long id);

    Product createProduct (Product product);

    Product updateProduct (Long id, Product product);

    boolean deleteById (Long id);


}
