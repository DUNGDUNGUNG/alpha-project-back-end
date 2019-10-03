package com.codegym.alphaprojectbackend.service.impl;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import com.codegym.alphaprojectbackend.repository.HouseRepository;
import com.codegym.alphaprojectbackend.service.HouseService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseRepository houseRepository;

    @Override
    public void save(House home) {
        houseRepository.save(home);
    }

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public House findById (Long id) throws EntityNotFoundException {
        return houseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public House findByHomeName(String houseName) {
        return houseRepository.findByHouseName(houseName);
    }

    @Override
    public House findByAddressHome(String addressHouse) {
        return houseRepository.findByAddressHouse(addressHouse);
    }

    @Override
    public Iterable<House> findAllByBathRoom(Integer bathRoom) {
        return houseRepository.findAllByBathRoom(bathRoom);
    }

    @Override
    public Iterable<House> findAllByBedRoom(Integer bedRoom) {
        return houseRepository.findAllByBedRoom(bedRoom);
    }

    @Override
    public Iterable<House> findAllByOwner(User user) {
        return houseRepository.findAllByOwner(user);
    }

    @Override
    public Iterable<House> findAllByPricePerNightBetween(Integer minPrice, Integer maxPrice) {
        return houseRepository.findAllByPricePerNightBetween(minPrice, maxPrice);
    }
}
