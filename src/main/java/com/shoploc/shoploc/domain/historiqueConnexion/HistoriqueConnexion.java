package com.shoploc.shoploc.domain.historiqueConnexion;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "historique_connexions")
public class HistoriqueConnexion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String clientEmail;
    private Date date;

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
