package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import com.shoploc.shoploc.domain.role.RoleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class ClientEntity extends AccountEntity {

   private boolean status_vfp;

   @OneToOne
   private CardEntity cardEntity;

   private Integer fidelityPoints;

    public ClientEntity(String firstname, String lastname, String email, String password, RoleEntity role, String image,boolean status_vfp, CardEntity cardEntity, Integer fidelityPoints) {
        super(firstname,lastname,email,password,role,image);
        this.status_vfp = status_vfp;
        this.cardEntity = cardEntity;
        this.fidelityPoints = fidelityPoints;
    }

    public ClientEntity(String firstname, String lastname, String email, String encodedPassword, RoleEntity role,String image) {
        super(firstname,lastname,email,encodedPassword,role,image);
    }

    public ClientEntity() {

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

    public Integer getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(Integer fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

}
