package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private ClientRepository clientRepository;


    public CardServiceImpl(CardRepository cardRepository, ClientRepository clientRepository) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public CardEntity createCard(AccountEntity accountEntity) {
        CardEntity card = new CardEntity();
        card.setDate(new Date());
        card.setMontant(0);
        return cardRepository.save(card);
    }

    @Override
    public ResponseEntity<ClientEntity> buy(String email, AchatEntity achatEntity) {
        int amount = (int) calculateTotalAmount(achatEntity);

        return clientRepository.findByEmail(email)
                .map(client -> updateClientBalance(client, amount))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    private double calculateTotalAmount(AchatEntity achatEntity) {
        return achatEntity.getCartItems().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }


    private ResponseEntity<ClientEntity> updateClientBalance(ClientEntity client, int amount) {
        CardEntity card = client.getCardEntity();
        if (card.getMontant() < amount) {
            return ResponseEntity.badRequest().build();
        }
        updateCardBalance(card, amount);
        updateClientFidelityPoints(client, amount);
        ClientEntity updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    private void updateCardBalance(CardEntity card, int amount) {
        card.setMontant(card.getMontant() - amount);
        cardRepository.save(card);
    }

    private void updateClientFidelityPoints(ClientEntity client, int amount) {
        client.setFidelityPoints(client.getFidelityPoints() + amount);
    }

    @Override
    public void fillHistory(AchatEntity achatEntity){

    }

    @Override
    public ResponseEntity<ClientEntity> creditCard(String email, Double amount) {
        var optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isPresent()) {
            ClientEntity clientToUpdate = optionalClient.get();
            Optional<CardEntity> card = cardRepository.findById(clientToUpdate.getCardEntity().getId());
            card.get().setMontant(card.get().getMontant() + amount);
            ClientEntity updatedClient = clientRepository.save(clientToUpdate);
            return ResponseEntity.ok(updatedClient);

        } else return ResponseEntity.badRequest().build();
    }
}
