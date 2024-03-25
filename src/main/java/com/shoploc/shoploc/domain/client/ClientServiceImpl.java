package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.avantage.AvantageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private AvantageRepository avantageRepository;

    public ClientServiceImpl(ClientRepository clientRepository, AvantageRepository avantageRepository) {
        this.clientRepository = clientRepository;
        this.avantageRepository = avantageRepository;
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

    @Override
    public Integer getFidelityPoints(String email) {
        var client = clientRepository.findByEmail(email);
        return client.get().getFidelityPoints();
    }

    @Override
    public ResponseEntity<ClientEntity> chooseAdvantage(String email, Integer advantage) {
        var client = clientRepository.findByEmail(email);
        if (client.isPresent()) {
            var avantage = avantageRepository.findById(advantage);
            client.get().setAvantage(avantage.get());
        }
        return ResponseEntity.ok(clientRepository.save(client.get()));
    }
}
