package com.shoploc.shoploc.domain.card;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    private double montant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public CardEntity() {
    }

    public CardEntity(Long id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public CardEntity(String email, double montant) {
        this.id = id;
        this.montant = montant;
    }
}