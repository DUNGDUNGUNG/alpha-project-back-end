package com.codegym.alphaprojectbackend.service.impl;

import com.codegym.alphaprojectbackend.model.House;
import com.codegym.alphaprojectbackend.model.Image;
import com.codegym.alphaprojectbackend.repository.ImageRepository;
import com.codegym.alphaprojectbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
  @Autowired
  ImageRepository imageRepository;

  @Override
  public Image save(Image image) {
    return imageRepository.save(image);
  }

  @Override
  public List<Image> findAllByHouse(House house) {
    return imageRepository.findAllByHouse(house);
  }

  @Override
  public Image findByImageUrl(String imageUrl) throws EntityNotFoundException {
    return imageRepository.findByImageUrl(imageUrl).orElseThrow(EntityNotFoundException::new);
  }
}
