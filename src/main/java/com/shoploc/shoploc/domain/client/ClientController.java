package com.shoploc.shoploc.domain.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    private  ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @PostMapping("{id}")
    public ResponseEntity<ClientEntity> creditClient(){
            return clientService.creditClient();
    }

    @PostMapping()
    public ResponseEntity<ClientEntity> debitClient(){
        return clientService.debitClient();
    }
}
