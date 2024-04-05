package com.shoploc.shoploc.domain.authent;

import com.shoploc.shoploc.dto.AccountDTO;
import com.shoploc.shoploc.dto.CredentialsDTO;
import com.shoploc.shoploc.exception.ObjectNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthentController {

    private final AuthentService authentService;

    @Autowired
    public AuthentController(AuthentService authentService) {
        this.authentService = authentService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AccountDTO> signIn(@RequestBody CredentialsDTO credentials) throws ObjectNotExistException {
        AccountDTO account = authentService.signIn(credentials);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<AccountDTO> validateToken(@RequestParam String token) throws ObjectNotExistException {
        AccountDTO account = authentService.validateToken(token);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
