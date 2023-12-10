package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.domain.role.RoleEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
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

import java.util.Date;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private JavaMailSender javaMailSender;
    private AccountMapper accountMapper;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

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
        if (accountRepository.findByEmail(account.getEmail()) != null) {
            throw new InsertionFailedException("Ce compte existe déja");
        } else {
            String password = RandomStringUtils.random(8, true, true);
            RoleEntity role = this.roleRepository.getReferenceById(Long.valueOf(roleId));
            account.setRole(role);
            account.setPassword(bCryptPasswordEncoder.encode(password));
            this.accountRepository.save(account);
            sendMessageByEmail(account, password);
        }
    }

    @Override
    public void modifyPasswordAccount(long id, String password) throws ModificationFailedException {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        try {
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
        message.setText("Voici vos identifiants ShopLoc\n   Login : " + account.getEmail() + "\n  Mot de passe : " + password);
        this.javaMailSender.send(message);
    }

    @Override
    public AccountDTO signIn(CredentialsDTO credentials) throws ObjectNotExistException {

        AccountEntity account = this.accountRepository.findByEmail(credentials.getEmail());
        AccountDTO accountToLogIn = accountMapper.toAccountDto(account);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(credentials.getPassword(), account.getPassword())) {
            accountToLogIn.setToken(createToken(accountToLogIn));
            accountToLogIn.setRoleId(Math.toIntExact((account.getRole().getRole_id())));
            accountToLogIn.setPassword("Tu ne trouveras rien ici :) ");
            return accountToLogIn;
        }
        throw new ObjectNotExistException("Identifiant et/ou mot de passe incorrect");
    }

    private String createToken(AccountDTO account) {
        Claims claims = Jwts.claims().setSubject(account.getEmail());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public AccountDTO validateToken(String token) throws ObjectNotExistException {
        try {
            String login = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            AccountEntity account = this.accountRepository.findByEmail(login);
            AccountDTO accountToLogIn = accountMapper.toAccountDto(account);
            accountToLogIn.setToken(createToken(accountToLogIn));
            accountToLogIn.setPassword("Tu ne trouveras rien ici :) ");
            return accountToLogIn;
        }
        catch (JwtException e) {
            throw new ObjectNotExistException("Invalid token:" + e);
        }
    }


}

