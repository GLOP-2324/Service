package com.shoploc.shoploc.domain.achat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class HistoriqueAchat {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

}
