package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountService {
    void createAccount(String firstname,String lastname,String email, Integer roleId, MultipartFile image, String address) throws InsertionFailedException, IOException;

    void modifyPasswordAccount(String email, String password) throws ModificationFailedException;

    void sendMessageByEmail(AccountEntity account, String password) throws InsertionFailedException;
    boolean deleteById (Integer id);

    AccountEntity findByEmail(String email);
}
