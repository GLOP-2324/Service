package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.avantage.AvantageEntity;
import com.shoploc.shoploc.avantage.AvantageRepository;
import com.shoploc.shoploc.domain.card.CardService;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.role.RoleEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.store.StoreService;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.mapper.AccountMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
    private CardService cardService;
    private JavaMailSender javaMailSender;

    private ClientRepository clientRepository;

    private AvantageRepository avantageRepository;
    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, AccountRepository accountRepository, JavaMailSender javaMailSender, StoreService storeService,CardService cardService,ClientRepository clientRepository, AvantageRepository avantageRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.storeService = storeService;
        this.javaMailSender = javaMailSender;
        this.cardService = cardService;
        this.clientRepository =clientRepository;
        this.avantageRepository = avantageRepository;
    }

    @Override
    @Transactional
    public void createAccount(String firstname,String lastname,String email ,Integer roleId,MultipartFile image, String address) throws InsertionFailedException, IOException {
        if (accountRepository.findByEmail(email) != null){
            throw new InsertionFailedException("Ce compte existe déja");

        } else {

            String encodedPassword = RandomStringUtils.random(8, true, true);
            RoleEntity role = this.roleRepository.getReferenceById(Long.valueOf(roleId));

            ClientEntity accountEntity= new ClientEntity();
            accountEntity.setFirstname(firstname);
            accountEntity.setLastname(lastname);
            accountEntity.setRole(role);
            accountEntity.setEmail(email);
            accountEntity.setDate_of_creation(new Date());
            accountEntity.setPassword(bCryptPasswordEncoder.encode(encodedPassword));
            if(role.getRole_id()==2){
                storeService.createStore(lastname+" "+firstname, email, image, address);
            }

            if(image==null){
                accountEntity.setImage(null);
            }
            else{
                String base64Image = convertToBase64(image);
                accountEntity.setImage(base64Image);
            }
             this.accountRepository.save(accountEntity);
            if(role.getRole_id()==3){
                AvantageEntity avantage = avantageRepository.getById(1);
                ClientEntity client = new ClientEntity(firstname,lastname,email,encodedPassword,role,accountEntity.getImage());
                var card  =cardService.createCard(client);
                accountEntity.setCardEntity(card);
                accountEntity.setFidelityPoints(0);
                accountEntity.setAvantage(avantage);
                this.clientRepository.save(accountEntity);
            }
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
    @Async
    public void sendMessageByEmail(AccountEntity account, String password) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setText("Votre compte ShopLoc a été créé", true);
            message.setText("Voici vos identifiants ShopLoc\n   Login : " + account.getEmail() + "\n  Mot de passe : " + password);
            helper.setTo(account.getEmail());
            helper.setSubject("Votre compte ShopLoc a été créé");
            helper.setFrom(new InternetAddress("projet_etu_fil@univ-lille.fr"));

        } catch (MessagingException e) {
        }
        this.javaMailSender.send(message);
    }

    @Override
    public boolean deleteById(Integer id) {
        if(accountRepository.findById(Long.valueOf(id)).isPresent()){
            accountRepository.deleteById(Long.valueOf(id));
            return true;
        }
        else return false;
    }

    @Override
    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] byteContent = file.getBytes();
        return Base64.getEncoder().encodeToString(byteContent);
    }
}

