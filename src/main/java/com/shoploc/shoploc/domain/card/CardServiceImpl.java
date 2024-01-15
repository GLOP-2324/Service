package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    @Override
    public void createCard(AccountEntity accountEntity) {
        CardEntity card = new CardEntity();
        card.setDate(new Date());
        card.setMontant(0);
        // card.setAccount(accountEntity);
        //accountEntity.setCard(card);
        cardRepository.save(card);
    }
}
