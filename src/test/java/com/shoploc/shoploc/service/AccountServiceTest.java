package com.shoploc.shoploc.service;


import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.role.RoleRepository;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.domain.account.AccountRepository;
import com.shoploc.shoploc.domain.account.AccountServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;

        import com.shoploc.shoploc.domain.role.RoleEntity;
        import com.shoploc.shoploc.domain.store.StoreService;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.web.multipart.MultipartFile;
        import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql("/insertData.sql")
class AccountServiceTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_Success() throws InsertionFailedException, IOException {
        when(accountRepository.findByEmail(anyString())).thenReturn(null);
        when(roleRepository.getReferenceById(anyLong())).thenReturn(new RoleEntity());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        MultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "test content".getBytes(StandardCharsets.UTF_8)
        );

        accountService.createAccount("John", "Doe", "john@example.com", 1, mockFile);

        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }


    @Test
    void createAccount_AccountAlreadyExists() throws InsertionFailedException, IOException {
        when(accountRepository.findByEmail(anyString())).thenReturn(new AccountEntity());
        assertThrows(InsertionFailedException.class, () ->
                accountService.createAccount("John", "Doe", "john@example.com", 1, mock(MultipartFile.class)));
    }

    @Test
    void modifyPasswordAccount_Success() throws ModificationFailedException {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountRepository.getReferenceById(anyLong())).thenReturn(new AccountEntity());
        accountService.modifyPasswordAccount(1, "newPassword");
        verify(accountRepository, times(1)).getReferenceById(anyLong());
    }

    @Test
    void modifyPasswordAccount_InvalidEmail() {
        when(accountRepository.getReferenceById(anyLong())).thenThrow(new RuntimeException());
        assertThrows(ModificationFailedException.class, () ->
                accountService.modifyPasswordAccount(1, "newPassword"));
    }
}
