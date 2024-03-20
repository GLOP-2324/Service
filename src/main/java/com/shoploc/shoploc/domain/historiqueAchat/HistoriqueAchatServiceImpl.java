package com.shoploc.shoploc.domain.historiqueAchat;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.product.Product;
import com.shoploc.shoploc.domain.product.ProductRepository;
import com.shoploc.shoploc.domain.store.StoreRepository;
import com.shoploc.shoploc.dto.HistoriqueAchatClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoriqueAchatServiceImpl implements HistoriqueAchatService {

    private HistoriqueAchatRepository historiqueAchatRepository;
    private StoreRepository storeRepository;
    private ProductRepository productRepository;

    @Autowired
    public HistoriqueAchatServiceImpl(HistoriqueAchatRepository historiqueAchatRepository, StoreRepository storeRepository, ProductRepository productRepository){
        this.historiqueAchatRepository = historiqueAchatRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

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
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                Product product1 = product.get();
                double price = product1.getPrice();
                historiqueAchat.setSpentMoney(quantity * price);
            }
            this.historiqueAchatRepository.save(historiqueAchat);
        });
    }

    @Override
    public List<HistoriqueAchatClientDTO> getHistoriqueAchat(String clientEmail) {
        List<HistoriqueAchat> historiqueAchatByClientId = this.historiqueAchatRepository.findByClientEmail(clientEmail);
        List<HistoriqueAchatClientDTO> historiqueAchatClientDTOList = new ArrayList<>();
        historiqueAchatByClientId.forEach(historiqueAchat -> {
            String storeName = this.storeRepository.findById(Long.valueOf(historiqueAchat.getStoreId())).get().getName();
            String productName = this.productRepository.findById(Long.valueOf(historiqueAchat.getProductId())).get().getLibelle();
            historiqueAchatClientDTOList.add(getHistoriqueAchatClientDTO(historiqueAchat, productName, storeName));
        });
        return historiqueAchatClientDTOList;
    }

    private static HistoriqueAchatClientDTO getHistoriqueAchatClientDTO(HistoriqueAchat historiqueAchatByClientId, String productName, String storeName) {
        HistoriqueAchatClientDTO historiqueAchatClientDTO = new HistoriqueAchatClientDTO();
        historiqueAchatClientDTO.setClientEmail(historiqueAchatByClientId.getClientEmail());
        historiqueAchatClientDTO.setDate(historiqueAchatByClientId.getDate().toString().substring(0,16));
        historiqueAchatClientDTO.setProductName(productName);
        historiqueAchatClientDTO.setStoreName(storeName);
        historiqueAchatClientDTO.setQuantity(historiqueAchatByClientId.getQuantity());
        historiqueAchatClientDTO.setMoneySpent(historiqueAchatByClientId.getSpentMoney());
        return historiqueAchatClientDTO;
    }
}
