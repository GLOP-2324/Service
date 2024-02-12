package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.io.File;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<Product> products;

    private String name;

    private Integer longitude;
    private Integer latitude;
    //todo : longitude and lan
    @Lob
    private String image;

    private String email;

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store() {

    }
    public Store(Integer id) {
        this.id = id;
    }
}
