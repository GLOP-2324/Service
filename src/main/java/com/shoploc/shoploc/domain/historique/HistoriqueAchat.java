package com.shoploc.shoploc.domain.historique;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "historique_achat")
public class HistoriqueAchat {
    @Id
    private Long id;

    @Getter
    private Integer storeId;
    private String clientEmail;
    private Integer productId;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public Integer getProductId() {
        return productId;
    }

    public Date getDate() {
        return date;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setDate(Date date){
        this.date=date;
    }
}
