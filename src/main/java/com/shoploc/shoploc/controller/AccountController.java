package com.shoploc.shoploc.controller;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
