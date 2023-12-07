package com.shoploc.shoploc.service;


import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.account.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
        AccountEntity account = new AccountEntity();
        account.setEmail("test@example.com");
        accountService.createAccount(account,1);
        Mockito.verify(accountRepository).save(account);
    }

    @Test
    void modifyPasswordAccount_success() throws ModificationFailedException {
        long accountId = 1L;
        String newPassword = "testPass";
        String encodedPassword = "encodedPassword";

        Mockito.when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        AccountEntity mockAccount = new AccountEntity();
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(mockAccount);

        accountService.modifyPasswordAccount(accountId, newPassword);

        Mockito.verify(bCryptPasswordEncoder).encode(newPassword);
        Mockito.verify(accountRepository).getReferenceById(accountId);
    }

    @Test
    void modifyPasswordAccount_error() {
        long accountId = 1L;
        String newPassword = "testPass";
        Mockito.when(accountRepository.getReferenceById(accountId)).thenThrow(new RuntimeException("Mail invalid"));

        assertThrows(ModificationFailedException.class, () -> {
            accountService.modifyPasswordAccount(accountId, newPassword);
        });
    }

}
