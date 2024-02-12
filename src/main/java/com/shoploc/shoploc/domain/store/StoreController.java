package com.shoploc.shoploc.domain.store;
import com.shoploc.shoploc.domain.account.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private StoreService storeService;

    @Autowired
    public StoreController (StoreService storeService) {
        this.storeService = storeService;
    }
    @GetMapping("email/{email}")
    public ResponseEntity<Store> getStoreByEmail(@PathVariable String email) {
        Store store = storeService.findByEmail(email);
        if (store != null) {
            return ResponseEntity.ok(store);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Void> createStore(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) throws IOException {
         storeService.createStore(
              name,email,image,0.0,0.0
        );
        return new ResponseEntity<>( HttpStatus.CREATED);
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

