package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public ResponseEntity<ClientEntity> creditClient() {
        return clientRepository.save()
    }

    @Override
    public ResponseEntity<ClientEntity> debitClient() {

    }

    @Override
    public void addPoints() {

    }
}
