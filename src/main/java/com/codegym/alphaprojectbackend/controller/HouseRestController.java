package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/house")
public class HouseRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HouseService houseService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<House>> showAllHouse() {
        Iterable<House> houses = houseService.findAll();
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getHouseDetailById(@PathVariable Long id) {
        try {
            House house = houseService.findById(id);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{name}/name")
    public ResponseEntity getHouseDetailByHomeName(@PathVariable String name) {
        try {
            House house = houseService.findByHomeName(name);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{address}/address")
    public ResponseEntity getHouseDetailByAddressHome(@PathVariable String address) {
        try {
            House house = houseService.findByAddressHome(address);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{number}/bathRoom")
    public ResponseEntity getHouseDetailByBathRoom(@PathVariable Integer number) {
        try {
            Iterable<House> houses = houseService.findAllByBathRoom(number);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{number}/bedRoom")
    public ResponseEntity getHouseDetailByBedRoom(@PathVariable Integer number) {
        try {
            Iterable<House> houses = houseService.findAllByBedRoom(number);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{min}/price/{max}")
    public ResponseEntity getHouseDetailByPricePerNightBetween(@PathVariable Integer min, @PathVariable Integer max) {
        try {
            Iterable<House> houses = houseService.findAllByPricePerNightBetween(min, max);
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
