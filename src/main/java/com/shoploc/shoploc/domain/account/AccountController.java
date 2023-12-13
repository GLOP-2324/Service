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
                                                @RequestParam("image") MultipartFile image) throws InsertionFailedException, IOException {

        System.out.println(image+"****************************************************************");
        this.accountService.createAccount(firstname,lastname,email,roleId,image);
        return ResponseEntity.status(HttpStatus.OK).body("Le compte à été crée avec succès");
    }

    @PatchMapping("password/{id}")
    public ResponseEntity<String> modifyAccountPassword(
            @PathVariable long id,
            @RequestBody AccountEntity account
    ) throws ModificationFailedException {
        if (id != account.getAccount_id()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mismatched IDs in path and request body");
        }
        this.accountService.modifyPasswordAccount(id, account.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Le mot de passe a été modifié avec succès");
    }
    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}
