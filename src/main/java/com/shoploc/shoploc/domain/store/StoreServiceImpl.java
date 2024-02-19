package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    public void createStore(String name, String email, MultipartFile image,String address) throws IOException {
        List<Product> products = new ArrayList<>();
        Store store= new Store();
        String base64Image = convertToBase64(image);
        store.setName(name);
        store.setEmail(email);
        store.setAddress(address);
        store.setProducts(products);
        store.setImage(base64Image);
        storeRepository.save(store);
    }


    @Override
    public Store updateStore(Long id, Store store) {
        Store existingStore = storeRepository.findById(id).orElse(null);

        if (existingStore != null) {
            existingStore.setName(store.getName());
            existingStore.setAddress(existingStore.getAddress());
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

    @Override
    @Transactional
    public Store findByEmail(String email) {
        return storeRepository.findByEmail(email);
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}
