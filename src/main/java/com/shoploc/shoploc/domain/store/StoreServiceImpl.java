package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.account.AccountService;
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
    private AccountRepository accountRepository;
    public StoreServiceImpl (StoreRepository storeRepository ,AccountRepository accountService) {
        this.storeRepository = storeRepository;
        this.accountRepository=accountService;
    }
    @Transactional
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
    @Transactional
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

    @Transactional
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
    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        if (storeRepository.findById(Long.valueOf(id)).isPresent()) {
            Store store = storeRepository.findById(Long.valueOf(id)).orElse(null);
            AccountEntity accountEntity = accountRepository.findByEmail(store.getEmail());
            accountRepository.deleteById(accountEntity.getAccount_id());
            storeRepository.deleteById(Long.valueOf(id));
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
