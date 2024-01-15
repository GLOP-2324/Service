package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<ClientEntity> creditClient();
    ResponseEntity<ClientEntity> debitClient();
}
