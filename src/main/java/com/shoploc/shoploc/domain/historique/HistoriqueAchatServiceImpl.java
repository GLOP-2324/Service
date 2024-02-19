package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoriqueAchatServiceImpl implements HistoriqueAchatService {

    private HistoriqueAchatRepository historiqueAchatRepository;

    public HistoriqueAchatServiceImpl(HistoriqueAchatRepository historiqueAchatRepository) {
        this.historiqueAchatRepository = historiqueAchatRepository;
    }

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

    }
}
