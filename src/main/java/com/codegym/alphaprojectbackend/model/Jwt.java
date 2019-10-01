package com.codegym.alphaprojectbackend.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
public class Jwt implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> roles;

    public Jwt(String accessToken, String username, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }

}
