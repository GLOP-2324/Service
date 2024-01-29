package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.card.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    private CardService cardService;

    public ClientController(ClientService clientService, CardService cardService) {
        this.clientService = clientService;
        this.cardService = cardService;
    }


    /*
    When charging the card we put charging to true, when buying we set it to false
    Post 1/card?amount=2&charging=true
    * */
    @PostMapping("{id}/card")
    public ResponseEntity<ClientEntity> creditClient(@PathVariable Long id, @RequestParam Integer amount, @RequestParam boolean charging) {
        if (charging)
            return cardService.creditCard(id, amount);
        else
            return cardService.debitCard(id, amount);
    }
}
