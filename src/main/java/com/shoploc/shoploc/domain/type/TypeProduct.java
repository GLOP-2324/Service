package com.shoploc.shoploc.domain.type;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class TypeProduct {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String libelle;

    @OneToMany
    private List<Product> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public TypeProduct(Integer id) {
        this.id = id;
    }
    public TypeProduct(){}
}
