package com.example.avito.service.impl.admin;

import com.example.avito.entity.User;
import com.example.avito.repository.AdminRepository;
import com.example.avito.service.AdminService;
import com.example.avito.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final RoleService roleService;

    private final static String USER_NOT_FOUND = "user not found";

    @Override
    public <T> String convertObjectsToJson(List<T> objects) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error converting users to JSON";
        }
    }

    @Override
    public List<User> getAllUsers() {
        return adminRepository.findAll();
    }

    @Override
    public ResponseEntity<?> setAdminRole(Long id) {
        Optional<User> userOptional = adminRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().clear();
            user.getRoles().add(roleService.getAdminRole());
            adminRepository.save(user);
            return ResponseEntity.ok().body(String.format("Роль администратора установлена для пользователя с email: %s, id: %d", user.getEmail(), user.getId()));
        } else {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> setUserRole(Long id) {
        Optional<User> userOptional = adminRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().clear();
            user.getRoles().add(roleService.getUserRole());
            adminRepository.save(user);
            return ResponseEntity.ok().body(String.format("Роль юзера установлена для пользователя с email: %s, id: %d", user.getEmail(), user.getId()));
        } else {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
    }
}
