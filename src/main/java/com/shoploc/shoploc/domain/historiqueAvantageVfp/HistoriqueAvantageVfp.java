package com.shoploc.shoploc.domain.historiqueAvantageVfp;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Entity
@Table(name = "historique_avantage_vfp")
public class HistoriqueAvantageVfp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientEmail;

    private LocalDate date_of_day;

    private boolean statut_Vfp;

    private Integer avantage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDate getDate_of_day() {
        return date_of_day;
    }

    public void setDate_of_day(LocalDate date_of_day) {
        this.date_of_day = date_of_day;
    }

    public boolean statut_Vfp() {
        return statut_Vfp;
    }

    public void setStatut_Vfp(boolean is_Statut_Vfp) {
        this.statut_Vfp = is_Statut_Vfp;
    }

    public Integer getAvantage() {
        return avantage;
    }

    public void setAvantage(Integer avantage) {
        this.avantage = avantage;
    }
}