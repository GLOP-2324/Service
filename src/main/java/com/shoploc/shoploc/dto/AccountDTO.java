package com.shoploc.shoploc.dto;

import com.shoploc.shoploc.domain.role.RoleEntity;

public class AccountDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleEntity role;
    private int roleId;
    private String token;
    private String image;
    private boolean vfp;

    private Long avantage;

    public Long getAvantage() {
        return avantage;
    }

    public void setAvantage(Long avantage) {
        this.avantage = avantage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isVfp() {
        return vfp;
    }

    public void setVfp(boolean vfp) {
        this.vfp = vfp;
    }
}
