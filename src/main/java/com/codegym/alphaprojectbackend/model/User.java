package com.codegym.alphaprojectbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "enabled", columnDefinition = "TINYINT(1)")
    private boolean enabled;

    @OneToMany( targetEntity = House.class)
    private Set<House> homes ;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Transient
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role currentRole : this.roles) {
            authorities.add(new SimpleGrantedAuthority(currentRole.getName()));
        }
        return authorities;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }
}