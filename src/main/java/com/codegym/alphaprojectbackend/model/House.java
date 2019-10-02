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

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean isRented;

    @Enumerated(EnumType.STRING)
    private HouseStatus status;

    public House() {
    }

    public House(String houseName, String addressHouse, Integer bedRoom, Integer bathRoom, String descriptionHouse, Integer pricePerNight, User owner, Category category, Boolean isRented, HouseStatus status) {
        this.houseName = houseName;
        this.addressHouse = addressHouse;
        this.bedRoom = bedRoom;
        this.bathRoom = bathRoom;
        this.descriptionHouse = descriptionHouse;
        this.pricePerNight = pricePerNight;
        this.owner = owner;
        this.category = category;
        this.isRented = isRented;
        this.status = status;
    }
}
