package com.codegym.alphaprojectbackend.service;

import com.codegym.alphaprojectbackend.model.Category;

import javax.persistence.EntityNotFoundException;

public interface CategoryService extends Service<Category> {
    Category findByName(String name) throws EntityNotFoundException;
}
