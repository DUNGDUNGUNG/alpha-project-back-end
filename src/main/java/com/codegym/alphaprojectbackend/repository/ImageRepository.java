package com.codegym.alphaprojectbackend.repository;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
  List<Image> findAllByHouse(House house);
  Optional<Image> findByImageUrl(String imageUrl);
}
