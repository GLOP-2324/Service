package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {
    private StoreService storeService;
    public StoreController (StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public Optional<Store> getByStore(@PathVariable Long id) {
        return storeService.getById(id);
    }

    @GetMapping("/test/1")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        if(stores != null ) {
            return new ResponseEntity<>(storeService.getAllStores(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Store> createConcert(@RequestBody Store store) {
        Store createdStore = storeService.createStore(
                store.getName(),
                store.getAddress()
        );
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteStoreById(@PathVariable Long id) {
        storeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Store updateStore (@PathVariable Long id, @RequestBody Store store) {
        return storeService.updateStore(id,
                store.getName(),
                store.getAddress()
        );
    }

}

