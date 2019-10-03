package com.codegym.alphaprojectbackend.service.impl;

import com.codegym.alphaprojectbackend.model.Role;
import com.codegym.alphaprojectbackend.repository.RoleRepository;
import com.codegym.alphaprojectbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
