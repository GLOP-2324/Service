package com.shoploc.shoploc.domain.type;


import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class TypeProduct {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String libelle;

    @OneToMany(mappedBy = "type")
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
