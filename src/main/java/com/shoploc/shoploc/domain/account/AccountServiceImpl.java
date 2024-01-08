package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.domain.role.RoleEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.domain.store.StoreService;
import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.exception.ObjectNotExistException;
import com.shoploc.shoploc.mapper.AccountMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;

    private StoreService storeService;
    private JavaMailSender javaMailSender;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, AccountRepository accountRepository, JavaMailSender javaMailSender, AccountMapper accountMapper,StoreService storeService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.storeService = storeService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void createAccount(String firstname,String lastname,String email ,Integer roleId,MultipartFile image) throws InsertionFailedException, IOException {
        if (accountRepository.findByEmail(email) != null){
            throw new InsertionFailedException("Ce compte existe déja");
        } else {

            String encodedPassword = RandomStringUtils.random(8, true, true);
            RoleEntity role = this.roleRepository.getReferenceById(Long.valueOf(roleId));

            AccountEntity accountEntity= new AccountEntity();
            accountEntity.setFirstname(firstname);
            accountEntity.setLastname(lastname);

            accountEntity.setRole(role);
            accountEntity.setEmail(email);
            accountEntity.setPassword(bCryptPasswordEncoder.encode(encodedPassword));
            if(role.getRole_id()==2){
                storeService.createStore(firstname, email, image);
            }
            if(image==null){
                accountEntity.setImage(null);
            }
            else{
                String base64Image = convertToBase64(image);
                accountEntity.setImage(base64Image);
            }
            this.accountRepository.save(accountEntity);
            sendMessageByEmail(accountEntity, encodedPassword);
        }
    }

    @Override
    public void modifyPasswordAccount(String email, String password) throws ModificationFailedException {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        try {
            AccountEntity account =  this.accountRepository.findByEmail(email);
            account.setPassword(encodedPassword);
            this.accountRepository.save(account);
        } catch (Exception e) {
            throw new ModificationFailedException("Invalid email");
        }
    }

    @Override
    public void sendMessageByEmail(AccountEntity account, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setSubject("Votre compte ShopLoc a été créé");
        message.setText("Voici vos identifiants ShopLoc\n   Login : " + account.getEmail() + "\n  Mot de passe : " + password);
        this.javaMailSender.send(message);
    }
    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}

