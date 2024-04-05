package com.shoploc.shoploc.domain.historiqueAchat;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.dto.HistoriqueAchatClientDTO;

import java.util.List;

public interface HistoriqueAchatService {
    void fillHistory(AchatEntity achatEntity);
    List<HistoriqueAchatClientDTO> getHistoriqueAchat(String clientEmail);

}
