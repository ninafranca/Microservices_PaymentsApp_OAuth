package com.paymentsApp.OAuth.models;

import java.util.List;

public class ClientDTO {

    private Long id;
    private String email;
    private String password;
    private List<RoleDTO> roles;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String email, String password, List<RoleDTO> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

}
