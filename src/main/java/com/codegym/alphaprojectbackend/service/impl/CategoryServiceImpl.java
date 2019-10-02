package com.codegym.alphaprojectbackend.service.impl;

import com.codegym.alphaprojectbackend.model.Category;
import com.codegym.alphaprojectbackend.repository.CategoryRepository;
import com.codegym.alphaprojectbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) throws EntityNotFoundException {
        return categoryRepository.findByName(name);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) throws EntityNotFoundException {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
