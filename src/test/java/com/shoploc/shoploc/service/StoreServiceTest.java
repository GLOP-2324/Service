package com.shoploc.shoploc.service;

import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.domain.store.StoreService;
import com.shoploc.shoploc.domain.store.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllStores() {
        when(storeRepository.findAll()).thenReturn(Arrays.asList(new Store(), new Store()));

        List<Store> result = storeService.getAllStores();

        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    void getById_StoreNotExists() {

        Long storeId = 1L;
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        Store result = storeService.getById(storeId);

        assertNull(result);
    }

    @Test
    void createStore() {
        String name = "Test Store";
        String address = "Store Address";
        File image = new File("/path/to/image");


        Store result = storeService.createStore(name, address, image);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(address, result.getAddress());
        assertEquals(image, result.getImage());
        verify(storeRepository, times(1)).save(result);
    }

    @Test
    void updateStore_StoreExists() {
        // Setup
        Long storeId = 1L;
        Store existingStore = new Store();
        existingStore.setId(storeId);

        Store updatedStore = new Store();
        updatedStore.setId(storeId);
        updatedStore.setName("Updated Store");

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(existingStore));
        when(storeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Store result = storeService.updateStore(storeId, updatedStore);

        assertNotNull(result);
        assertEquals(updatedStore.getId(), result.getId());
        assertEquals(updatedStore.getName(), result.getName());
    }

    @Test
    void deleteById_StoreExists() {

        Long storeId = 1L;
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(new Store()));

        // Test
        boolean result = storeService.deleteById(storeId);

        assertTrue(result);
        verify(storeRepository, times(1)).deleteById(storeId);
    }

}

