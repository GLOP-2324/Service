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
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {
    private StoreService storeService;
    public StoreController (StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getByStore(@PathVariable Long id) {
        if (storeService.getById(id) != null) {
            return new ResponseEntity<>(storeService.getById(id),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
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
                store.getAddress(),
                store.getImage()
        );
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreById(@PathVariable Long id) {
        boolean deleted = storeService.deleteById(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore (@PathVariable Long id, @RequestBody Store store) {
        Store storeToUpdate = storeService.updateStore(id, store);
        if (storeToUpdate != null) {
            return new ResponseEntity<>(storeToUpdate, HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }

}

