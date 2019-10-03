package com.codegym.alphaprojectbackend.service;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {

    void save(User user);

    Iterable<User> findAll();

    Optional<User> findByEmail(String email);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    User getUserByAuth();

    User findByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    User findByEmailIgnoreCase(String email);

    List<User> findUsersByHouses(House houses);

    Long count();
}

