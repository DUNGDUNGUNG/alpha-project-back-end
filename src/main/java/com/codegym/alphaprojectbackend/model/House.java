package com.codegym.alphaprojectbackend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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

    @OneToMany(targetEntity = Image.class,cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private List<Image> images;

    public House() {
    }

    public House(@NotBlank @Size(min = 2, max = 50) String name, @NotBlank @Size(min = 2) String address, Integer bedRooms, Integer bathRooms, @NotBlank @Size(min = 2) String description, Integer pricePerNight, List<Image> images, Boolean isRented, HouseStatus status, Category category, User owner) {
        this.houseName = name;
        this.addressHouse = address;
        this.bedRoom = bedRooms;
        this.bathRoom = bathRooms;
        this.descriptionHouse = description;
        this.pricePerNight = pricePerNight;
        this.images = images;
        this.isRented = isRented;
        this.status = status;
        this.category = category;
        this.owner = owner;
    }

    public House(@NotBlank @Size(min = 2, max = 50) String name, @NotBlank @Size(min = 2) String address, Integer bedRooms, Integer bathRooms, @NotBlank @Size(min = 2, max = 50) String description, Integer pricePerNight) {
        this.houseName = name;
        this.addressHouse = address;
        this.bedRoom = bedRooms;
        this.bathRoom = bathRooms;
        this.descriptionHouse = description;
        this.pricePerNight = pricePerNight;
        this.status = HouseStatus.AVAILABLE;
    }
}
