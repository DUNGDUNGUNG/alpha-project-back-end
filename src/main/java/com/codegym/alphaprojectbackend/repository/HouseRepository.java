package com.codegym.alphaprojectbackend.repository;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    House findByHouseName(String houseName);
    House findByAddressHouse(String addressHouse);
    List<House> findAllByBathRoom(Integer bathRoom);
    List<House> findAllByBedRoom(Integer bedRoom);
    List<House> findAllByOwner(User user);
    List<House> findAllByPricePerNightBetween(Integer minPrice, Integer maxPrice);
}
