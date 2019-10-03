package com.codegym.alphaprojectbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "email", unique = true)
    private String email;

    @Past
    private Date birthday;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    private String address;

    @Column(name = "password")
    private String password;

    private String avatarUrl;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @Column(name = "enabled", columnDefinition = "TINYINT(1)")
    private boolean enabled;

    @OneToMany( targetEntity = House.class)
    private Set<House> houses ;

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

    public User(@Size(min = 3, max = 50) String lastName, @Size(min = 3, max = 50) String firstName, @NotBlank @Size(max = 50) @Email String email, @Past Date birthday, Gender gender, String address, @NotBlank @Size(min = 8, max = 15) String password, String avatarUrl, @Pattern(regexp = "0([0-9]{9})") String phoneNumber, Set<Role> roles) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
    }
}