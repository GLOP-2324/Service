package com.shoploc.shoploc.controller;

import com.shoploc.shoploc.entity.Store;
import com.shoploc.shoploc.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/{id}")
    public Optional<Store> getByStore(@PathVariable Long id) {
        return storeService.getById(id);
    }

    @GetMapping("/all")
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @PostMapping("/createStore")
    public Store createConcert(@RequestBody Store store) {
        Store createdStore = storeService.createStore(
                store.getName(),
                store.getAddress()
        );
        return createdStore;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStoreById(@PathVariable Long id) {
        storeService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Store updateStore (@PathVariable Long id, @RequestBody Store store) {
        return storeService.updateStore(id,
                store.getName(),
                store.getAddress()
        );
    }

}

