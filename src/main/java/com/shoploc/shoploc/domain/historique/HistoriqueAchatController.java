package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.product.Product;
import com.shoploc.shoploc.dto.HistoriqueAchatClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historique")
public class HistoriqueAchatController {

    private final HistoriqueAchatService historiqueAchatService;

    @Autowired
    public HistoriqueAchatController(HistoriqueAchatService historiqueAchatService) {
        this.historiqueAchatService = historiqueAchatService;
    }


    @GetMapping("/{clientEmail}")
    public ResponseEntity<List<HistoriqueAchatClientDTO>> getHistoriqueAchat(@PathVariable String clientEmail) {
        return new ResponseEntity<>(historiqueAchatService.getHistoriqueAchat(clientEmail),HttpStatus.OK);
    }
}
