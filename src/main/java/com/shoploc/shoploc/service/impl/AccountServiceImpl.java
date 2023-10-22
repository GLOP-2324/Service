package com.shoploc.shoploc.service.impl;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.repository.AccountRepository;
import com.shoploc.shoploc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AccountRepository accountRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Account account) throws InsertionFailedException {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder password = new StringBuilder();
        for (int i=0; i<20; i++){
            password.append(characters.charAt((int) Math.floor(Math.random()*characters.length())));
        }
        //TODO: send email ...

        account.setPassword(bCryptPasswordEncoder.encode(password));
        this.accountRepository.save(account);
        if (accountRepository.findById(account.getAccount_id()).isEmpty()){
            throw new InsertionFailedException("Ce compte existe déja");
        }
    }
}
