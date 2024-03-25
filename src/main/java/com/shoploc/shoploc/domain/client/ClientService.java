package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<ClientEntity> addPoints(Long id,Integer amount);
    Integer getFidelityPoints(String email);

    ResponseEntity<ClientEntity> chooseAdvantage(String email, Integer Advantage);

    ResponseEntity<ClientEntity> chooseAdvantage(String email, Integer Advantage);

}
