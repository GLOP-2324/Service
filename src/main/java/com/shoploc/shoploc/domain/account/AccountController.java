package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;
    private final String className = this.getClass().getSimpleName();


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createAccount(@RequestBody AccountEntity account, @RequestParam Integer roleId) throws InsertionFailedException {
        this.accountService.createAccount(account,roleId);
        return ResponseEntity.status(HttpStatus.OK).body("Le compte à été crée avec succès");
    }

    @PatchMapping("/{id}")
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

}
