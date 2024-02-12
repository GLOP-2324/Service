package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.account.AccountEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    List<Store> getAllStores();

    Store getById(Long id);

    void createStore (String name, String email, MultipartFile image,Double longitude,Double latitude) throws IOException;

    Store updateStore (Long id, Store store);

    boolean deleteById (Long id);

    Store findByEmail(String email);
}
