package com.shoploc.shoploc.domain.type;


import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class TypeProduct {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String libelle;

    @OneToMany(mappedBy = "type")
    private List<Product> products;

    public TypeProduct () {
        this.products = new ArrayList<>();
    }

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

    public List<Product> setProducts(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        return products;
    }

}
