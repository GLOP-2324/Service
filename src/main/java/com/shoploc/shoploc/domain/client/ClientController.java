package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import com.shoploc.shoploc.domain.card.CardService;
import com.shoploc.shoploc.domain.historique.HistoriqueAchatService;
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


    @PostMapping("{email}/card")
    public ResponseEntity<ClientEntity> creditOrBuy(@PathVariable String email, @RequestParam(required = false) Double amount, @RequestBody(required = false) AchatEntity achatEntity,@RequestParam(required = false) boolean creditCard)  {
        if (achatEntity == null && amount != null) {
                return cardService.creditCard(email, amount);
        } else if (!creditCard){
        	this.historiqueAchatService.fillHistory(achatEntity);
            return cardService.buyWithFidelityCard(email, achatEntity);
        }
        this.historiqueAchatService.fillHistory(achatEntity);
        return cardService.buyWithCreditCard(email, achatEntity);
    }


    @GetMapping("{email}/card")
    public ResponseEntity<CardEntity> getCardInfo(@PathVariable String email)  {
        return cardService.getCardInformation(email);
    }

    @PatchMapping("{email}/card")
    public ResponseEntity<CardEntity> buyWithFidelityPoints(@PathVariable String email, @RequestParam(required = false) Double amount)  {
        return cardService.buyWithFidelityPoints(email, amount);
    }

}
