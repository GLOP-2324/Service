package com.shoploc.shoploc.domain.authent;

import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.ObjectNotExistException;

public interface AuthentService {

    AccountDTO signIn(CredentialsDTO credentials) throws ObjectNotExistException;
    AccountDTO validateToken(String token) throws ObjectNotExistException;
}
