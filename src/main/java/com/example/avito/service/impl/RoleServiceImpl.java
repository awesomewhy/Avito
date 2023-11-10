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

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }
}
