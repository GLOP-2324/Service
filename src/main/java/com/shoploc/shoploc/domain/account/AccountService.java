package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;

public interface AccountService {
    void createAccount(AccountEntity accountEntity, Integer roleId) throws InsertionFailedException;

    void modifyPasswordAccount(long id, String password) throws ModificationFailedException;

    void sendMessageByEmail(AccountEntity account, String password);
}
