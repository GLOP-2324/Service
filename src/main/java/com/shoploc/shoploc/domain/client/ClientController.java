package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.card.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private  ClientService clientService;

    private CardService cardService;

    public ClientController(ClientService clientService,CardService cardService){
        this.clientService = clientService;
        this.cardService=cardService;
    }
    @PostMapping("{id}/card")
    public ResponseEntity<ClientEntity> creditClient(@PathVariable Long id, @RequestParam Integer amount){
            return cardService.creditOrDebutClient(id,amount);
    }

    @PostMapping("{id}")
    public ResponseEntity<ClientEntity> addFidelityPoints(@PathVariable Long id,@RequestParam Integer amount){
        return clientService.addPoints(id,amount);
    }

}
