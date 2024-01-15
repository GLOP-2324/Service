package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class ClientEntity extends AccountEntity {

   private boolean status_vfp;

   @OneToOne
   private CardEntity cardEntity;

   private float fidelityPoints;

    public ClientEntity(boolean status_vfp, CardEntity cardEntity, float fidelityPoints) {
        this.status_vfp = status_vfp;
        this.cardEntity = cardEntity;
        this.fidelityPoints = fidelityPoints;
    }

    public boolean isStatus_vfp() {
        return status_vfp;
    }

    public void setStatus_vfp(boolean status_vfp) {
        this.status_vfp = status_vfp;
    }

    public CardEntity getCardEntity() {
        return cardEntity;
    }

    public void setCardEntity(CardEntity cardEntity) {
        this.cardEntity = cardEntity;
    }

    public float getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(float fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public ClientEntity() {
    }
}
