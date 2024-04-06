package com.shoploc.shoploc.product;

import com.shoploc.shoploc.domain.product.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_Success() {
        int storeId = 1;
        List<Product> productList = new ArrayList<>();
        when(productRepository.findByStoreId(eq(storeId))).thenReturn(productList);

        List<Product> result = productService.getAllProducts(storeId);
        verify(productRepository, times(1)).findByStoreId(eq(storeId));

    }

    @Test
    void getById_ProductExists() {
        long productId = 1;
        Product product = new Product();
        when(productRepository.findById(eq(productId))).thenReturn(java.util.Optional.of(product));

        Product result = productService.getById(productId);

        verify(productRepository, times(2)).findById(eq(productId));
    }

    @Test
    void createProduct_Success() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);

        Product product = new Product();
        product.setLibelle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setImage("base64Image");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ProductExists() {
        long productId = 1;
        Product existingProduct = new Product();
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(existingProduct));

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setLibelle("Updated Product");

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        verify(productRepository, times(1)).findById(eq(productId));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteById_ProductExists() {

        long productId = 1;
        Product existingProduct = new Product();
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(existingProduct));

        boolean result = productService.deleteById(productId);
        verify(productRepository, times(1)).findById(eq(productId));
        verify(productRepository, times(1)).deleteById(eq(productId));
    }

}
