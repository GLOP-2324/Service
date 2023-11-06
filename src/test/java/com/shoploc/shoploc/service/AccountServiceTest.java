package com.shoploc.shoploc.service;


import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.repository.AccountRepository;
import com.shoploc.shoploc.service.impl.AccountServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createAccount_doSuccess() throws InsertionFailedException {
        Account account = new Account();
        account.setEmail("test@example.com");
        accountService.createAccount(account);
        Mockito.verify(accountRepository).save(account);
    }

    @Test
    void modifyPass_doSucess() throws ModificationFailedException {
        long accountId = 1L;
        String newPassword = "testPass";
        Account account = new Account();
        account.setAccount_id(accountId);
        Mockito.when(accountRepository.getReferenceById(1l)).thenReturn(account);
        Mockito.when(bCryptPasswordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        Mockito.verify(account).getPassword();}

}
