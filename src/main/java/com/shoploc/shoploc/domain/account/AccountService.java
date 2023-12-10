package com.shoploc.shoploc.domain.account;

import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.InsertionFailedException;
import com.shoploc.shoploc.exception.ModificationFailedException;
import com.shoploc.shoploc.exception.ObjectNotExistException;

public interface AccountService {
    void createAccount(AccountEntity accountEntity, Integer roleId)  throws InsertionFailedException ;

    void modifyPasswordAccount(long id, String password) throws ModificationFailedException;

    void sendMessageByEmail(AccountEntity account, String password) throws InsertionFailedException;

    AccountDTO signIn(CredentialsDTO credentials) throws ObjectNotExistException;

    AccountDTO validateToken(String token) throws ObjectNotExistException;
}
