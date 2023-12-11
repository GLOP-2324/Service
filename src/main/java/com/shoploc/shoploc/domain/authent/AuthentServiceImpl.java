package com.shoploc.shoploc.domain.authent;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.ObjectNotExistException;
import com.shoploc.shoploc.mapper.AccountMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthentServiceImpl implements AuthentService {

    private AccountMapper accountMapper;
    private AccountRepository accountRepository;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Autowired
    public AuthentServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository=accountRepository;
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

}
