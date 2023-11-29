package com.example.avito.service;

import com.example.avito.entity.Role;

import java.util.Collection;
import java.util.Optional;

public interface RoleService {
    Optional<Role> getUserRole();
    Optional<Role> getAdminRole();
}
