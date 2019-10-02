package com.codegym.alphaprojectbackend.service;

import javax.persistence.EntityNotFoundException;

public interface Service<H> {
    void save(H h);

    Iterable<H> findAll();

    H findById(Long id) throws EntityNotFoundException;

    void delete(Long id);
}
