package com.shoploc.shoploc.service;


import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.repository.AccountRepository;
import com.shoploc.shoploc.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/insertData.sql")
public class AccountServiceTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Test
    void createAccount_doSuccess() throws InsertionFailedException {
        Account account = new Account();
        account.setEmail("joh@example.com");
        accountService.createAccount(account);

        Mockito.verify(accountRepository).save(account);
    }

}
