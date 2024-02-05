package com.shoploc.shoploc.domain.store;

import com.shoploc.shoploc.domain.product.Product;
import jakarta.persistence.*;

import java.io.File;
import java.util.List;
import java.util.Set;


@Entity
public class Store {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private List<Product> products;

    private String name;

    private String address;
//todo : longitude and lan
    @Lob
    private String image;

    private String email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
