package com.shoploc.shoploc.domain.product;

import com.shoploc.shoploc.domain.store.Store;
import com.shoploc.shoploc.domain.type.TypeProduct;
import jakarta.persistence.*;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String libelle;

    private String description;
    //todo qte
    private double price;

    private int points;
    @Lob
    private String image;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeProduct type;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Boolean getBenefitsActivated() {
        return benefitsActivated;
    }

    public void setBenefitsActivated(Boolean benefitsActivated) {
        this.benefitsActivated = benefitsActivated;
    }

    private Boolean benefitsActivated = false;

    private int stock;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public TypeProduct getType() {
        return type;
    }

    public void setType(TypeProduct type) {
        this.type = type;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public Product(Long id, String libelle, String description, double price, String image, TypeProduct type, Store store, Integer points, Boolean benefitsActivated, Integer stock) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.price = price;
        this.image = image;
        this.type = type;
        this.store = store;
        this.benefitsActivated = benefitsActivated;
        this.points = points;
        this.stock = stock;
    }

    public Product(){}
}
