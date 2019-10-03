package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.*;
import com.codegym.alphaprojectbackend.repository.ConfirmationTokenRepository;
import com.codegym.alphaprojectbackend.service.JwtService;
import com.codegym.alphaprojectbackend.service.RoleService;
import com.codegym.alphaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserRestController {

    private static final String DEFAULT_ROLE = "USER";

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterForm registerForm) {
        if (userService.existsByEmail(registerForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        //create
        this.begin();
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        Role role = roleService.findRoleByName(DEFAULT_ROLE);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return new ResponseEntity<>("Created!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(),((UserPrinciple) userDetails).getAvatarUrl());
        return ResponseEntity.ok(jwtResponse);
    }

    private void begin(){
        if (!roleService.existsByName(DEFAULT_ROLE)) {
            roleService.save(new Role(DEFAULT_ROLE));
        }

        if (!roleService.existsByName("ADMIN")) {
            roleService.save(new Role("ADMIN"));
        }

        if (userService.count() == 0) {
            User userBegin = new User();
            userBegin.setId(Long.valueOf(1));
            userBegin.setEmail("admin@gmail.com");
            userBegin.setPassword("admin");
            Role role = roleService.findRoleByName("ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            userBegin.setRoles(roles);
            userService.save(userBegin);
        }
    }
}
