package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseEntity<ClientEntity> addPoints(Long id, Integer amount) {
        var client = clientRepository.findById(id);
        if(client.isPresent())
        {
            client.get().setFidelityPoints(client.get().getFidelityPoints() + amount);
        }
        return ResponseEntity.ok(clientRepository.save(client.get()));
    }
}
