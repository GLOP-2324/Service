package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<ClientEntity> addPoints(Long id,Integer amount);

}
