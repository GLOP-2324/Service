package com.shoploc.shoploc.controller;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;
    private final String className = this.getClass().getSimpleName();


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) throws InsertionFailedException {
        this.accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body("Le compte à été crée avec succès");
    }

    @PostMapping("/modifyAccountPassword")
    public ResponseEntity<String> modifyAccountPassword(@RequestParam int id, @RequestParam String mail, @RequestParam String password) throws ModificationFailedException {
        this.accountService.modifyPasswordAccount(id,mail,password);
        return ResponseEntity.status(HttpStatus.OK).body("Le mot de passe à été modifié avec succès");
    }
}
