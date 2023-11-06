package com.shoploc.shoploc.service.impl;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.repository.AccountRepository;
import com.shoploc.shoploc.service.AccountService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountRepository accountRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AccountRepository accountRepository, JavaMailSender javaMailSender) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void createAccount(Account account) throws InsertionFailedException {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()){
            throw new InsertionFailedException("Ce compte existe déja");
        }
        else{
            String password = RandomStringUtils.random(8, true, true);
            sendMessageByEmail(account,password);

            account.setPassword(bCryptPasswordEncoder.encode(password));
            this.accountRepository.save(account);
        }
    }
    @Override
    public void modifyPasswordAccount(long id, String password) throws ModificationFailedException {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        try{
            this.accountRepository.getReferenceById(id).setPassword(encodedPassword);
        } catch (Exception e) {
            throw new ModificationFailedException("Mail invalide");
        }
    }

    @Override
    public void sendMessageByEmail(Account account,String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setSubject("Votre compte ShopLoc a été créé");
        message.setText("Voici vos identifiants ShopLoc\n   Login : "+account.getEmail()+"\n  Mot de passe : "+password);
        this.javaMailSender.send(message);
    }
}
