package com.shoploc.shoploc.domain.authent;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.historiqueConnexion.HistoriqueConnexion;
import com.shoploc.shoploc.domain.historiqueConnexion.HistoriqueConnexionRepository;
import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.ObjectNotExistException;
import com.shoploc.shoploc.mapper.AccountMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthentServiceImpl implements AuthentService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private HistoriqueConnexionRepository historiqueConnexionRepository;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Autowired
    public AuthentServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository, HistoriqueConnexionRepository historiqueConnexionRepository,ClientRepository clientRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository=accountRepository;
        this.historiqueConnexionRepository=historiqueConnexionRepository;
        this.clientRepository=clientRepository;
    }

    @Override
    @Transactional
    public AccountDTO signIn(CredentialsDTO credentials) throws ObjectNotExistException {
        AccountEntity account = this.accountRepository.findByEmail(credentials.getEmail());
        ClientEntity clientEntity = this.clientRepository.findByEmail(credentials.getEmail()).orElse(null);
        if(account !=null ) {
            AccountDTO accountToLogIn = accountMapper.toAccountDto(account);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(credentials.getPassword(), account.getPassword())) {
                accountToLogIn.setToken(createToken(accountToLogIn));
                accountToLogIn.setRoleId(Math.toIntExact((account.getRole().getRole_id())));
                if (accountToLogIn.getRole().getRole_id() == 3){
                    accountToLogIn.setVfp(clientEntity.isStatus_vfp());
                    accountToLogIn.setAvantage(clientEntity.getAvantage().getAvantage_id());
                }
                accountToLogIn.setPassword("Tu ne trouveras rien ici :) ");

                HistoriqueConnexion historiqueConnexion = new HistoriqueConnexion();
                historiqueConnexion.setClientEmail(credentials.getEmail());
                historiqueConnexion.setDate(new Date());
                this.historiqueConnexionRepository.save(historiqueConnexion);
                return accountToLogIn;
            }
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
