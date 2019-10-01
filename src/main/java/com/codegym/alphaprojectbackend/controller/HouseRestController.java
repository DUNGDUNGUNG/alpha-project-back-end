package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.service.HouseService;
import com.codegym.alphaprojectbackend.service.JwtService;
import com.codegym.alphaprojectbackend.service.RoleService;
import com.codegym.alphaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HouseRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HouseService houseService;

    @GetMapping("/houses")
    public ResponseEntity<Iterable<House>> showAllHouse                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     () {
        Iterable<House> houses = houseService.findAll();
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("houses/{id}")
    public ResponseEntity getHouseDetailById(@PathVariable Long id) {
        try {
            House house = houseService.findById(id);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("houses/{name}/name")
    public ResponseEntity getHouseDetailByHomeName(@PathVariable String name) {
        try {
            House house = houseService.findByHomeName(name);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("houses/{address}/address")
    public ResponseEntity getHouseDetailByAddressHome(@PathVariable String address) {
        try {
            House house = houseService.findByAddressHome(address);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("houses/{number}/bathRoom")
    public ResponseEntity getHouseDetailByBathRoom(@PathVariable Integer number) {
        try {
            Iterable<House> houses = houseService.findAllByBathRoom(number);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("houses/{number}/bedRoom")
    public ResponseEntity getHouseDetailByBedRoom(@PathVariable Integer number) {
        try {
            Iterable<House> houses = houseService.findAllByBedRoom(number);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("houses/{min}/price/{max}")
    public ResponseEntity getHouseDetailByPricePerNightBetween(@PathVariable Integer min, @PathVariable Integer max) {
        try {
            Iterable<House> houses = houseService.findAllByPricePerNightBetween(min, max);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
