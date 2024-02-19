package com.shoploc.shoploc.service;


import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.role.RoleEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.account.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Sql("/insertData.sql")
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void createAccount_doSuccess() throws InsertionFailedException, IOException {
        // Given
        AccountEntity account = new AccountEntity();
        account.setEmail("test@example.com");
        // Mocking behavior using roleRepository
        when(roleRepository.getReferenceById(anyLong())).thenReturn(new RoleEntity(1L,"test")); // Mocking the role retrieval

        // When
        accountService.createAccount("Nadine", "Saadi", "nad@yahoo.com", 1, (MultipartFile) null, (double) 0, (double) 0);

        // Then
        verify(accountRepository).save(any(AccountEntity.class));
    }

    @Test
    void modifyPasswordAccount_success() throws ModificationFailedException {
        String mail = "nad@yahoo.com";
        String newPassword = "testPass";
        String encodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        AccountEntity mockAccount = new AccountEntity();
        when(accountRepository.findByEmail(mail)).thenReturn(mockAccount);

        accountService.modifyPasswordAccount(mail, newPassword);

        verify(bCryptPasswordEncoder).encode(newPassword);
        verify(accountRepository).findByEmail(mail);
    }

    @Test
    void modifyPasswordAccount_error() {
        String mail = "nad@yahoo.com";
        String newPassword = "testPass";
        when(accountRepository.findByEmail(mail)).thenThrow(new RuntimeException("Mail invalid"));

        assertThrows(ModificationFailedException.class, () -> {
            accountService.modifyPasswordAccount(mail, newPassword);
        });
    }

}
