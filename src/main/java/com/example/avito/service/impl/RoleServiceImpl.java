package com.example.avito.service.impl;

import com.example.avito.entity.Role;
import com.example.avito.repository.RoleRepository;
import com.example.avito.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final static String ROLE_USER = "ROLE_USER";
    private final static String ROLE_ADMIN = "ROLE_ADMIN";


    public Role getUserRole() {
        return roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));
    }
    public Role getAdminRole() {
        return roleRepository.findByName(ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
    }
}
