package com.shoploc.shoploc.domain.client;

import com.shoploc.shoploc.avantage.AvantageEntity;
import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import com.shoploc.shoploc.domain.product.Product;
import com.shoploc.shoploc.domain.role.RoleEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "client")
public class ClientEntity extends AccountEntity {

    private boolean status_vfp;

    private LocalDate date_of_validity_vfp ;

    @OneToOne
    private CardEntity cardEntity;

    private Integer fidelityPoints;

    @OneToMany
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "avantage_id")
    private AvantageEntity avantage;

    public ClientEntity(String firstname, String lastname, String email, String password, RoleEntity role, String image, boolean status_vfp, CardEntity cardEntity, Integer fidelityPoints, List<Product> products) {
        super(firstname, lastname, email, password, role, image);
        this.status_vfp = status_vfp;
        this.cardEntity = cardEntity;
        this.fidelityPoints = fidelityPoints;
        this.products = products;
    }

    public ClientEntity(String firstname, String lastname, String email, String encodedPassword, RoleEntity role, String image) {
        super(firstname, lastname, email, encodedPassword, role, image);
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDate getDate_of_validity_vfp() {
        return date_of_validity_vfp;
    }

    public void setDate_of_validity_vfp(LocalDate date_of_validity_vfp) {
        this.date_of_validity_vfp = date_of_validity_vfp;
    }

    public AvantageEntity getAvantage() {
        return avantage;
    }

    public void setAvantage(AvantageEntity avantage) {
        this.avantage = avantage;
    }
}
