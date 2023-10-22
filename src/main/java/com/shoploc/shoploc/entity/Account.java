package com.shoploc.shoploc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Data
@Transactional
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Long role_id;

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccount_id() {
        return account_id;
    }

}
