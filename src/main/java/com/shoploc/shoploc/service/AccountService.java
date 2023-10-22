package com.shoploc.shoploc.service;

import com.shoploc.shoploc.entity.Account;
import com.shoploc.shoploc.exception.InsertionFailedException;

public interface AccountService {
    void createAccount(Account account) throws InsertionFailedException;
}
