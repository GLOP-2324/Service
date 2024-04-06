package com.shoploc.shoploc.service;

import com.shoploc.shoploc.domain.type.TypeProduct;
import com.shoploc.shoploc.domain.type.TypeProductRepository;
import com.shoploc.shoploc.domain.type.TypeProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TypeProductServiceTest {

    @Mock
    private TypeProductRepository typeProductRepository;

    @InjectMocks
    private TypeProductServiceImpl typeProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTypes() {
        TypeProduct type1 = new TypeProduct(1);
        TypeProduct type2 = new TypeProduct(2);
        when(typeProductRepository.findAll()).thenReturn(Arrays.asList(type1, type2));

        List<TypeProduct> result = typeProductService.getAllTypes();
        assertEquals(2, result.size());
        assertEquals(type1, result.get(0));
        assertEquals(type2, result.get(1));
    }


   /* @Test
    void createType() {
        String libelle = "Test Type";
        TypeProduct result = typeProductService.createType(libelle);
        assertNotNull(result);
        assertEquals(libelle, result.getLibelle());
        verify(typeProductRepository, times(1)).save(result);
    }*/


}
