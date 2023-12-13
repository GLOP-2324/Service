package com.shoploc.shoploc.service;

import com.shoploc.shoploc.domain.product.Product;
import com.shoploc.shoploc.domain.product.ProductRepository;
import com.shoploc.shoploc.domain.product.ProductServiceImpl;
import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.domain.type.TypeProduct;
import com.shoploc.shoploc.domain.type.TypeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private TypeProductRepository typeProductRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {

        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));


        List<Product> result = productService.getAllProducts();


        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetById_ProductExists() {

        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));


        Product result = productService.getById(productId);

        assertNotNull(result);
        assertEquals(product, result);
    }


    @Test
    public void testCreateProduct() {

        String libelle = "Test Product";
        String description = "Product Description";
        Double price = 19.99;
        File image = new File("/Users/maminiang/Downloads/pokeball.png");
        System.out.println(image);
        Store store = new Store();
        store.setEmail("test@store.com");
        TypeProduct type = new TypeProduct();

        when(storeRepository.findByEmail(store.getEmail())).thenReturn(store);

       Product result = productService.createProduct(libelle, description, price, type, image, store);

        assertNotNull(result);
        assertEquals(libelle, result.getLibelle());
        assertEquals(description, result.getDescription());
        assertEquals(price, result.getPrice());
        assertEquals(image, result.getImage());
    }


    @Test
    public void testUpdateProduct_ProductExists() {

        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setLibelle("Updated Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getLibelle(), result.getLibelle());
    }


    @Test
    public void testDeleteById_ProductExists() {

        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        boolean result = productService.deleteById(productId);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testDeleteById_ProductNotExists() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        boolean result = productService.deleteById(productId);

        assertFalse(result);
        verify(productRepository, never()).deleteById(productId);
    }

}
