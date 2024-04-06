package com.shoploc.shoploc.batch;

import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.historiqueAchat.HistoriqueAchat;
import com.shoploc.shoploc.domain.historiqueAvantageVfp.HistoriqueAvantageVfp;
import com.shoploc.shoploc.domain.historiqueVfpAcquisitionAndExpiration.HistoriqueVfp;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class VfpProcessor implements ItemProcessor<Pair<List<HistoriqueAchat>, ClientEntity>, ClientEntity> {

    @Value("${vfp.minus.orders}")
    private int minimumOrdersToEnableVFP;

    private HistoriqueAvantageVfp historiqueAvantageVfp;

    @Override
    public ClientEntity process(Pair<List<HistoriqueAchat>, ClientEntity> pair)  {
        return getCustomer(pair, this.minimumOrdersToEnableVFP);
    }

    public ClientEntity getCustomer(Pair<List<HistoriqueAchat>, ClientEntity> pair, int minimumOrdersToEnableVFP) {
        ClientEntity clientEntity = pair.getSecond();


        boolean updateStatus = false;
        LocalDate newValidityDate = null;

        if (pair.getFirst().size() >= minimumOrdersToEnableVFP) {
            updateStatus = true;
            newValidityDate = LocalDate.now().plusDays(7);
        } else if (clientEntity.isStatus_vfp() && clientEntity.getDate_of_validity_vfp() != null && LocalDate.now().isBefore(clientEntity.getDate_of_validity_vfp())) {
            updateStatus = true; // Keep existing status_vfp as true
        }

        if (updateStatus) {
            clientEntity.setStatus_vfp(true);
            clientEntity.setDate_of_validity_vfp(newValidityDate);
       } else {
            clientEntity.setStatus_vfp(false);
        }

        return clientEntity;
    }

}
