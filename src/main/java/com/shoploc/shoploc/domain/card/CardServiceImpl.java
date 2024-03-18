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
    public ResponseEntity<ClientEntity> buyWithCreditCard(String email, AchatEntity achatEntity) {
        var optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isPresent()) {
            ClientEntity clientToUpdate = optionalClient.get();
            ClientEntity updatedClient = clientRepository.save(clientToUpdate);
            return ResponseEntity.ok(updatedClient);
        } else return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<ClientEntity> buyWithFidelityCard(String email, AchatEntity achatEntity,Boolean buyWithfidelityPoints) {
        var products = achatEntity.getCartItems();
        var optionalClient = clientRepository.findByEmail(email);
        double amount = products.stream().mapToDouble(Product::getPrice).sum();
        int fidelityPointsTotal = buyWithfidelityPoints ?
                products.stream().filter(p-> p.getPoints()!=null).mapToInt(Product::getPoints).sum()
        : 0 ;
        amount -= fidelityPointsTotal;
        if (optionalClient.isPresent()) {
            ClientEntity clientToUpdate = optionalClient.get();
            Optional<CardEntity> clientCard = cardRepository.findById(clientToUpdate.getCardEntity().getId());
            if (clientCard.get().getMontant() > 0 && clientCard.get().getMontant() >= amount) {
                clientCard.get().setMontant(clientCard.get().getMontant() - amount);
                cardRepository.save(clientCard.get());
                clientToUpdate.setFidelityPoints(fidelityPointsTotal);
                ClientEntity updatedClient = clientRepository.save(clientToUpdate);
                return ResponseEntity.ok(updatedClient);
            } else return ResponseEntity.badRequest().build();
        } else return ResponseEntity.badRequest().build();
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
    @Override
    public ResponseEntity<CardEntity> getCardInformation(String email) {
        var optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isPresent()) {
            ClientEntity clientToUpdate = optionalClient.get();
            Optional<CardEntity> card = cardRepository.findById(clientToUpdate.getCardEntity().getId());
            CardEntity cardEntity = card.get();
            return ResponseEntity.ok(cardEntity);

        } else return ResponseEntity.badRequest().build();
    }

}
