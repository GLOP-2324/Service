package com.shoploc.shoploc.domain.historiqueVfpAcquisitionAndExpiration;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "historique_vfp")
public class HistoriqueVfp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientEmail;

    private LocalDate date_of_acquisition;

    private LocalDate date_of_expiration;
}
