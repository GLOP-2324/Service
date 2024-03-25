package com.shoploc.shoploc.avantage;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="avantage")
public class AvantageEntity {

    public AvantageEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avantage_id;
    @Column(nullable = false, unique = true)
    private String avantage_name;

    public AvantageEntity(Long avantage_id, String avantage_name) {
        this.avantage_id = avantage_id;
        this.avantage_name = avantage_name;
    }

    public Long getAvantage_id() {
        return avantage_id;
    }

    public void setAvantage_id(Long avantage_id) {
        this.avantage_id = avantage_id;
    }

    public String getAvantage_name() {
        return avantage_name;
    }

    public void setAvantage_name(String avantage_name) {
        this.avantage_name = avantage_name;
    }
}