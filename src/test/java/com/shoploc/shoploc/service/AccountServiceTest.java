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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void modifyPasswordAccount_success() throws ModificationFailedException {
        long accountId = 1L;
        String newPassword = "testPass";
        String encodedPassword = "encodedPassword";

        // Mock interactions with dependencies
        Mockito.when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        Account mockAccount = new Account();
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(mockAccount);

        // Perform the method under test
        accountService.modifyPasswordAccount(accountId, newPassword);

        // Verify that the password was set on the mock account
        Mockito.verify(bCryptPasswordEncoder).encode(newPassword);
        Mockito.verify(accountRepository).getReferenceById(accountId);
    }
    @Test
    void modifyPasswordAccount_error()  {
        long accountId = 1L;
        String newPassword = "testPass";
        Mockito.when(accountRepository.getReferenceById(accountId)).thenThrow(new RuntimeException("Mail invalid"));

        assertThrows(ModificationFailedException.class, () -> {
            accountService.modifyPasswordAccount(accountId, newPassword);
        });
    }

}
