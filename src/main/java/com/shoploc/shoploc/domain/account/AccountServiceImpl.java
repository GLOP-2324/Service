package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.domain.role.RoleEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.mapper.AccountMapper;
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

    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    private  AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private  JavaMailSender javaMailSender;
    private AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, AccountRepository accountRepository, JavaMailSender javaMailSender, AccountMapper accountMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createAccount(AccountEntity account, Integer roleId) throws InsertionFailedException {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()){
            throw new InsertionFailedException("Ce compte existe déja");
        }
        else{
            String password = RandomStringUtils.random(8, true, true);
            RoleEntity role = this.roleRepository.getReferenceById(Long.valueOf(roleId));
            account.setRole(role);
            account.setPassword(bCryptPasswordEncoder.encode(password));
            this.accountRepository.save(account);
            sendMessageByEmail(account,password);
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
    public void sendMessageByEmail(AccountEntity account, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setSubject("Votre compte ShopLoc a été créé");
        message.setText("Voici vos identifiants ShopLoc\n   Login : "+account.getEmail()+"\n  Mot de passe : "+password);
        this.javaMailSender.send(message);
    }
}
