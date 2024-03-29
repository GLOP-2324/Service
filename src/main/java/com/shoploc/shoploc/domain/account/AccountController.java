package com.shoploc.shoploc.domain.account;


import com.shoploc.shoploc.domain.store.StoreService;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;
    private StoreService storeService;



    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createAccount(@RequestParam("firstname") String firstname,
                                                @RequestParam ("lastname")String lastname,
                                                @RequestParam ("email")String email,
                                                @RequestParam ("roleId")Integer roleId,
                                                @RequestParam(name="image",required=false) MultipartFile image,
                                                @RequestParam(name="address",required=false) String address) throws InsertionFailedException, IOException {
        if(firstname == null || lastname == null || email == null || roleId ==null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required params missing");
        }
        try {
            this.accountService.createAccount(firstname, lastname, email, roleId, image, address);
            return ResponseEntity.status(HttpStatus.OK).body("Account created successfully");
        } catch (InsertionFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process image: " + e.getMessage());
        }
    }

    @PostMapping("/password")
    public ResponseEntity<String> modifyAccountPassword(@RequestBody AccountEntity account) throws ModificationFailedException {

        this.accountService.modifyPasswordAccount(account.getEmail(),account.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Le mot de passe a été modifié avec succès");
    }
    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}
