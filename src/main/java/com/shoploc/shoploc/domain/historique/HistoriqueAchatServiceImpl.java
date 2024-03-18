package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.product.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoriqueAchatServiceImpl implements HistoriqueAchatService {

    private HistoriqueAchatRepository historiqueAchatRepository;

    public HistoriqueAchatServiceImpl(HistoriqueAchatRepository historiqueAchatRepository) {
        this.historiqueAchatRepository = historiqueAchatRepository;
    }

    /*
    @Override
    public void fillHistory(AchatEntity achatEntity) {
        HistoriqueAchat historiqueAchat = new HistoriqueAchat();
        historiqueAchat.setClientEmail(achatEntity.getEmailUser());
        historiqueAchat.setStoreId(achatEntity.getStoreId());
        historiqueAchat.setDate(new Date());
        achatEntity.getCartItems().forEach(product -> {
            historiqueAchat.setProductId(Math.toIntExact(product.getId()));
            this.historiqueAchatRepository.save(historiqueAchat);
        });
    }*/

    public void fillHistory(AchatEntity achatEntity) {
        // Création d'une carte des quantités par produit
        Map<Long, Integer> quantityPerProduct = achatEntity.getCartItems().stream()
                .collect(Collectors.groupingBy(Product::getId, Collectors.summingInt(prod -> 1)));

        // Création de l'historique des achats pour chaque produit
        quantityPerProduct.forEach((productId, quantity) -> {
            HistoriqueAchat historiqueAchat = new HistoriqueAchat();
            historiqueAchat.setClientEmail(achatEntity.getEmailUser());
            historiqueAchat.setStoreId(achatEntity.getStoreId());
            historiqueAchat.setDate(new Date());
            historiqueAchat.setPurchaseDate(LocalDate.now());
            historiqueAchat.setProductId(Math.toIntExact(productId));
            historiqueAchat.setQuantity(quantity);
            this.historiqueAchatRepository.save(historiqueAchat);
        });
    }
}
