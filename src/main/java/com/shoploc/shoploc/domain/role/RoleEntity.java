package com.shoploc.shoploc.domain.role;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
@Data
@Table(name="role")
public class RoleEntity {
    public RoleEntity(){}

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    @Column(nullable = false, unique = true)
    private String role_name;

    public RoleEntity(Long role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }
}
