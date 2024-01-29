package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private ClientRepository clientRepository;

    public CardServiceImpl(CardRepository cardRepository, ClientRepository clientRepository) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void createCard(AccountEntity accountEntity) {
        CardEntity card = new CardEntity();
        card.setDate(new Date());
        card.setMontant(0);
        cardRepository.save(card);
    }


    @Override
    public ResponseEntity<ClientEntity> debitCard(Long id, Integer amount) {
        var optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            ClientEntity clientToUpdate = optionalClient.get();
            CardEntity card = new CardEntity(id, clientToUpdate.getCardEntity().getMontant() - amount);
            clientToUpdate.setCardEntity(card);
            clientToUpdate.setFidelityPoints(clientToUpdate.getFidelityPoints() + amount);
            ClientEntity updatedClient = clientRepository.save(clientToUpdate);
            return ResponseEntity.ok(updatedClient);
        }
        else return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<ClientEntity> creditCard(Long id, Integer amount) {
        var optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            ClientEntity clientToUpdate = optionalClient.get();
                CardEntity card = new CardEntity(id, clientToUpdate.getCardEntity().getMontant() + amount);
                clientToUpdate.setCardEntity(card);
                ClientEntity updatedClient = clientRepository.save(clientToUpdate);
                return ResponseEntity.ok(updatedClient);

        }
        else return ResponseEntity.badRequest().build();
    }
}
