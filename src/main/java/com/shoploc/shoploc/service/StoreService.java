package com.shoploc.shoploc.service;

import com.shoploc.shoploc.entity.Product;
import com.shoploc.shoploc.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    List<Store> getAllStores();

    Optional<Store> getById(Long id);

    Store createStore (String name, String address);

    Store updateStore (Long id, String name, String address);

    void deleteById (Long id);
}
