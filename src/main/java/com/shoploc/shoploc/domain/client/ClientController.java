package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.achat.AchatEntity;
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
 /*   @PostMapping("{email}/card")
    public ResponseEntity<ClientEntity> creditClient(@PathVariable String email, @RequestParam Integer amount, @RequestParam boolean charging) {
        if (charging)
            return cardService.creditCard(email, amount);
        else
            return cardService.debitCard(email, amount);
    }
*/
    @PostMapping("{email}/card")
    public ResponseEntity<ClientEntity> creditClient(@PathVariable String email,@RequestParam Double amount, @RequestBody AchatEntity achatEntity) {
        if (amount!=null)
            return cardService.creditCard(email,amount);
        else
            return cardService.buy(email, achatEntity);
    }
    //todo buy with credit card
}
