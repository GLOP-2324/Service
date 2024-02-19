package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.achat.AchatEntity;

public interface HistoriqueAchatService {
    void fillHistory(AchatEntity achatEntity);
}
