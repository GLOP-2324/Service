package com.shoploc.shoploc.service;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;

public interface AccountService {
    void createAccount(Account account) throws InsertionFailedException;

    void modifyPasswordAccount(long id, String password) throws ModificationFailedException;

    void sendMessageByEmail(Account account, String password);
}
