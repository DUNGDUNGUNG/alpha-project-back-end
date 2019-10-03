package com.codegym.alphaprojectbackend.service;

import com.codegym.alphaprojectbackend.model.Role;

public interface RoleService {
    Role findRoleByName(String roleName);

    Role save(Role role);

    Boolean existsByName(String name);
}
