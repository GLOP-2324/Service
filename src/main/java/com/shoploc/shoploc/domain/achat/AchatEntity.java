package com.shoploc.shoploc.domain.achat;

import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class AchatEntity {

    @Id
    private int id;
    private int storeId;
    private String emailUser;
    @ManyToMany
    private List<Product> cartItems;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Product> cartItems) {
        this.cartItems = cartItems;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
