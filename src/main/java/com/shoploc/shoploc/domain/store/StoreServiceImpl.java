package com.shoploc.shoploc.domain.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> getAllStores() {
        return (List<Store>) storeRepository.findAll();
    }

    @Override
    public Optional<Store> getById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Store createStore(String name, String address) {
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);

        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Long id, String name, String address) {
        Store existingStore = storeRepository.findById(id).orElse(null);

        if (existingStore != null) {
            existingStore.setName(name);
            existingStore.setAddress(address);
            return storeRepository.save(existingStore);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}
