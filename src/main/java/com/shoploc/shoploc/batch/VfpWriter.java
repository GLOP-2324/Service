package com.shoploc.shoploc.batch;

import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VfpWriter implements ItemWriter<ClientEntity> {

    private final ClientRepository clientRepository;

    @Autowired
    public VfpWriter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void write(Chunk<? extends ClientEntity> chunk) {
        this.clientRepository.saveAll(chunk.getItems());
    }
}