package com.codegym.alphaprojectbackend.service;


import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.Image;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ImageService {
  Image save(Image image);
  List<Image> findAllByHouse(House house);
  Image findByImageUrl(String imageUrl) throws EntityNotFoundException;
}
