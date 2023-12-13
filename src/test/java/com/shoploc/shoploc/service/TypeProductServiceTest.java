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

import static org.junit.jupiter.api.Assertions.*;
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

        when(typeProductRepository.findAll()).thenReturn(Arrays.asList(new TypeProduct(), new TypeProduct()));

        List<TypeProduct> result = typeProductService.getAllTypes();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createType() {
        String libelle = "Test Type";

        TypeProduct result = typeProductService.createType(libelle);

        assertNotNull(result);
        assertEquals(libelle, result.getLibelle());
        verify(typeProductRepository, times(1)).save(result);
    }
}
