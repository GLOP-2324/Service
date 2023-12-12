package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.store.Store;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface StoreService {

    List<Store> getAllStores();

    Store getById(Long id);

    Store createStore (String name, String address, File image);

    Store updateStore (Long id, Store store);

    boolean deleteById (Long id);
}
