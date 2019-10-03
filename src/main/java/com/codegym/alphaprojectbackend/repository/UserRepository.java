package com.codegym.alphaprojectbackend.repository;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    User findByEmailIgnoreCase(String email);

    List<User> findUsersByHouses(House houses);
}
