package com.shoploc.shoploc.batch;

import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.historiqueAchat.HistoriqueAchat;
import com.shoploc.shoploc.domain.historiqueAchat.HistoriqueAchatRepository;
import jakarta.transaction.Transactional;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class VfpReader implements ItemReader<Pair<List<HistoriqueAchat>, ClientEntity>> {
    private HistoriqueAchatRepository historiqueAchatRepository;
    private ClientRepository clientRepository;
    private List<ClientEntity> clients;
    private Iterator<ClientEntity> clientIterator;

    @Autowired
    public VfpReader(HistoriqueAchatRepository historiqueAchatRepository, ClientRepository clientRepository) {
        this.historiqueAchatRepository = historiqueAchatRepository;
        this.clientRepository = clientRepository;
    }

    public void init () {
        this.clients = this.clientRepository.findAll();
        this.clientIterator = clients.iterator();
    }

    @Override
    @Transactional
    public Pair<List<HistoriqueAchat>, ClientEntity> read() {
        System.out.println("toto");
        if (clients == null || !clientIterator.hasNext()) {
            System.out.println("tata");
            this.init();
            return null;
        }

        ClientEntity currentCustomer = clientIterator.next();
        return Pair.of(historiqueAchatRepository.findAllByClientEmailAndPurchaseDateAfter(currentCustomer.getEmail(), LocalDate.now().minusDays(7)), currentCustomer);
    }
}
