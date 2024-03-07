package com.shoploc.shoploc.domain.historique;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "historique_achat")
public class HistoriqueAchat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private Integer storeId;
    private String clientEmail;
    private Integer productId;
    private Date date;
    private Integer quantity;

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


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public void setDate(Date date) {
        this.date = date;
    }
}
