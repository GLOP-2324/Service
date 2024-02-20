package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.client.ClientEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.DoubleConsumer;

public interface CardService {
    CardEntity createCard (AccountEntity accountEntity);

    ResponseEntity<ClientEntity> buyWithFidelityCard(String email, AchatEntity achatEntity);
    ResponseEntity<ClientEntity> buyWithCreditCard(String email, AchatEntity achatEntity);
    ResponseEntity<ClientEntity> creditCard(String email, Double amount);

    ResponseEntity<CardEntity> getCardInformation(String email);
}
