package com.shoploc.shoploc.domain.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    public StoreServiceImpl (StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getAllStores() {
        return (List<Store>) storeRepository.findAll();
    }

    @Override
    public Store getById(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            return storeRepository.findById(id).get();
        }
        else return null;
    }

    @Override
    public Store createStore(String name, String address, File image) {
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);
        store.setImage(image);
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Long id, Store store) {
        Store existingStore = storeRepository.findById(id).orElse(null);

        if (existingStore != null) {
            existingStore.setName(store.getName());
            existingStore.setAddress(store.getAddress());
            existingStore.setImage(store.getImage());
            return storeRepository.save(existingStore);
        }

        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (storeRepository.findById(id).isPresent()) {
            storeRepository.deleteById(id);
            return true;
        }
        else return false;
    }
}
