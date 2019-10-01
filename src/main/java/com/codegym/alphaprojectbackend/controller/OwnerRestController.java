package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import com.codegym.alphaprojectbackend.service.HouseService;
import com.codegym.alphaprojectbackend.service.JwtService;
import com.codegym.alphaprojectbackend.service.RoleService;
import com.codegym.alphaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class OwnerRestController {

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

    @PostMapping(value = "/houses", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> createHouse(@ModelAttribute House house, HttpServletRequest request) {
        User user = userService.getUserByAuth();
        house.setOwner(user);
//        house.setStatus(HouseStatus.AVAILABLE);
//        house.setIsRented(false);
        houseService.save(house);
        return new ResponseEntity<>(new ResponseMessage("Publish House successfully"), HttpStatus.OK);
    }

    @GetMapping("list-house")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<House>> listHouse() {
        List<House> houses = (List<House>) houseService.findAll();
        if (houses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/house")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<House>> listHouseByUser() {
        User user = userService.getUserByAuth();
        List<House> houses = (List<House>) houseService.findAllByOwner(user);
        if (houses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<House> getHouse(@PathVariable("id") Long id) {
        try {
            House house = houseService.findById(id);
            return new ResponseEntity<House>(house,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteHouse(@PathVariable("id") Long id) {
        try {
            House house = houseService.findById(id);
            if (house.getIsRented()) {
                return new ResponseEntity<>(new ResponseMessage("Cannot delete house"), HttpStatus.BAD_REQUEST);
            }
            houseService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("Remove House successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}",consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> editHouse(@PathVariable("id") Long id, @ModelAttribute House house) {
        try {
            User user = userService.getUserByAuth();
            User owner = houseService.findById(id).getOwner();
            if (user.getId().equals(owner.getId())){
                house.setOwner(user);
                houseService.save(house);
                return new ResponseEntity<>(new ResponseMessage("Update House successfully"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseMessage("You are not owner of this house"),HttpStatus.FORBIDDEN);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

}
