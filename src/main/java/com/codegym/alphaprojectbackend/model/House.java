package com.codegym.alphaprojectbackend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "house")
@Data
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private Long id;

    @Column(name = "houseName")
    private String houseName;

    @Column(name = "kindHouse")
    private String kindHouse;

    @Column(name = "kindRoom")
    private String kindRoom;

    @Column(name = "addressHouse")
    private String addressHouse;

    @Column(name = "bedRoom")
    private Integer bedRoom;

    @Column(name = "bathRoom")
    private Integer bathRoom;

    @Column(name = "descriptionHouse")
    private String descriptionHouse;

    @Column(name = "pricePerNight")
    private Integer pricePerNight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private Boolean isRented;

    public House() {
    }

    public House(String houseName, String kindHouse, String kindRoom, String addressHouse, Integer bedRoom, Integer bathRoom, String descriptionHouse, Integer pricePerNight, User owner, Boolean isRented) {
        this.houseName = houseName;
        this.kindHouse = kindHouse;
        this.kindRoom = kindRoom;
        this.addressHouse = addressHouse;
        this.bedRoom = bedRoom;
        this.bathRoom = bathRoom;
        this.descriptionHouse = descriptionHouse;
        this.pricePerNight = pricePerNight;
        this.owner = owner;
        this.isRented = isRented;
    }
}
