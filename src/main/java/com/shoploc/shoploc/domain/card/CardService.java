package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.client.ClientEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CardService {
    void createCard (AccountEntity accountEntity);

    ResponseEntity<ClientEntity> debitCard(Long id, Integer amount);

    ResponseEntity<ClientEntity> creditCard(Long id, Integer amount);

}
