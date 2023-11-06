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
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public String getEmail() {
        return email;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
