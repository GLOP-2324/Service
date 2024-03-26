package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import com.shoploc.shoploc.domain.card.CardService;
import com.shoploc.shoploc.domain.historiqueAchat.HistoriqueAchatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    private CardService cardService;

    private HistoriqueAchatService historiqueAchatService;

    public ClientController(ClientService clientService, CardService cardService, HistoriqueAchatService historiqueAchatService) {
        this.clientService = clientService;
        this.cardService = cardService;
        this.historiqueAchatService = historiqueAchatService;
    }
    @GetMapping("{email}/fidelityPoints")
    public Integer getClientFidelityPoint(@PathVariable String email) {
        return clientService.getFidelityPoints(email);
    }

    @PostMapping("{email}/card")
    public ResponseEntity<ClientEntity> creditOrBuy(@PathVariable String email,
                                                    @RequestParam(required = false) Double amount,
                                                    @RequestBody(required = false) AchatEntity achatEntity,
                                                    @RequestParam(required = false) boolean creditCard,
                                                    @RequestParam(required = false) boolean buyWithfidelityPoints) {
        ResponseEntity<ClientEntity> responseEntity;

        if (achatEntity == null && amount != null && creditCard) {
            responseEntity = cardService.creditCard(email, amount);
        } else if (!creditCard) {
            responseEntity = cardService.buyWithFidelityCard(email, achatEntity, buyWithfidelityPoints);
        } else if (buyWithfidelityPoints){
            responseEntity = cardService.buyWithFidelityPoints(email, achatEntity);
        } else {
            responseEntity = cardService.buyWithCreditCard(email, achatEntity);
        }
        if (achatEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            this.historiqueAchatService.fillHistory(achatEntity);
        }
        return responseEntity;
    }


    @GetMapping("{email}/card")
    public ResponseEntity<CardEntity> getCardInfo(@PathVariable String email) {
        return cardService.getCardInformation(email);
    }

    @PutMapping("{email}")
    public ResponseEntity<ClientEntity> chooseAvantage (@PathVariable String email, @RequestParam(required = true) Integer avantage) {
        return clientService.chooseAdvantage(email,avantage);
    }

}
