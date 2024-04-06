package com.shoploc.shoploc.service;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.store.*;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    void getAllStores() {
        List<Store> stores = new ArrayList<>();
        when(storeRepository.findAll()).thenReturn(stores);

        List<Store> result = storeService.getAllStores();

        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void getById_StoreExists() {

        long storeId = 1;
        Store existingStore = new Store();
        when(storeRepository.findById(eq(storeId))).thenReturn(Optional.of(existingStore));

        Store result = storeService.getById(storeId);

        verify(storeRepository, times(2)).findById(eq(storeId));

    }

    @Test
    void getById_StoreNotExists() {

        long storeId = 1;
        when(storeRepository.findById(eq(storeId))).thenReturn(Optional.empty());

        Store result = storeService.getById(storeId);

        verify(storeRepository, times(1)).findById(eq(storeId));
    }

    @Test
    void createStore() throws IOException {

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn("test content".getBytes());


        storeService.createStore("StoreName", "store@example.com", mockFile,"");

        verify(storeRepository, times(1)).save(any(Store.class));

    }

    @Test
    void updateStore_StoreExists() {

        long storeId = 1L;
        Store existingStore = new Store();
        when(storeRepository.findById((long) eq(storeId))).thenReturn(Optional.of(existingStore));

        Store updatedStore = new Store();
        updatedStore.setId((int) storeId);
        updatedStore.setName("Updated Store");

        when(storeRepository.save(any(Store.class))).thenReturn(updatedStore);


        Store result = storeService.updateStore((long) storeId, updatedStore);

        verify(storeRepository, times(1)).findById((long) eq(storeId));
        verify(storeRepository, times(1)).save(any(Store.class));

    }


    @Test
    void deleteById_StoreExists() {
        long storeId = 1;

        // Mocking storeRepository.findById() to return a Store entity
        Store store = new Store();
        store.setId(1);
        store.setEmail("test@example.com");
        when(storeRepository.findById(eq(storeId))).thenReturn(Optional.of(store));

        // Mocking accountRepository.findByEmail() to return a non-null AccountEntity
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount_id(1L); // Set the account id
        when(accountRepository.findByEmail(anyString())).thenReturn(accountEntity);

        // Mocking deleteById method of accountRepository
        doNothing().when(accountRepository).deleteById(1L); // Correct the argument to match accountEntity.getAccount_id()

        boolean result = storeService.deleteById(1);

        // Verify that storeRepository.deleteById() is invoked
        verify(storeRepository, times(2)).findById(eq(storeId));
        verify(storeRepository, times(1)).deleteById(eq(storeId));

        // Verify that accountRepository.deleteById() is invoked
        verify(accountRepository, times(1)).deleteById(eq(accountEntity.getAccount_id()));

        assertTrue(result); // Assert that the method returns true
    }
}
