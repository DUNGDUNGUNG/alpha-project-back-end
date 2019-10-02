package com.codegym.alphaprojectbackend.service;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import com.querydsl.core.types.Predicate;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface HouseService extends Service<House> {

    void save(House home);

    Iterable<House> findAll();

    House findById(Long id) throws EntityNotFoundException;

    void delete(Long id);

    House findByHomeName(String homeName);

    House findByAddressHome(String addressHome);

    Iterable<House> findAllByBathRoom(Integer bathRoom);

    Iterable<House> findAllByBedRoom(Integer bedRoom);

    Iterable<House> findAllByOwner(User user);

    Iterable<House> findAllByPricePerNightBetween(Integer minPrice, Integer maxPrice);
}
