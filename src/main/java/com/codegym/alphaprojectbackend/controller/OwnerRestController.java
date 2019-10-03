package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.Category;
import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.HouseStatus;
import com.codegym.alphaprojectbackend.model.User;
import com.codegym.alphaprojectbackend.service.*;
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
    private CategoryService categoryService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HouseService houseService;

    @PostMapping(value = "/house", consumes = "multipart/form-data")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createHouse(@ModelAttribute House house, HttpServletRequest request) {
        User user = userService.getUserByAuth();
        house.setOwner(user);
        house.setStatus(HouseStatus.AVAILABLE);
        house.setIsRented(false);
        houseService.save(house);
        return new ResponseEntity<>(new ResponseMessage("Publish House successfully"), HttpStatus.OK);
    }

    @GetMapping("list-house")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<House>> listHouse() {
        List<House> houses = (List<House>) houseService.findAll();
        if (houses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/houses-list-user")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<House>> listHouseByUser() {
        User user = userService.getUserByAuth();
        List<House> houses = (List<House>) houseService.findAllByOwner(user);
        if (houses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping("/{id}/house")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<House> getHouse(@PathVariable("id") Long id) {
        try {
            House house = houseService.findById(id);
            return new ResponseEntity<House>(house,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/house")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @PutMapping(value = "/{id}/house",consumes = "multipart/form-data")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @GetMapping("list-category")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Category>> listCategory() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}/category")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.findById(id);
            return new ResponseEntity<Category>(category,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/category")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@ModelAttribute Category category, HttpServletRequest request) {
        User user = userService.getUserByAuth();
        categoryService.save(category);
        return new ResponseEntity<>(new ResponseMessage("Publish Category successfully"), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/category")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> editCategory(@PathVariable("id") Long id, @ModelAttribute Category category) {
        try {
            User user = userService.getUserByAuth();
            category.setId(id);
            categoryService.save(category);
            return new ResponseEntity<>(new ResponseMessage("Update Category successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/category")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.findById(id);
            categoryService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("Remove Category successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
