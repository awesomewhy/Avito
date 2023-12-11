package com.example.avito.service.impl.admin;

import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.repository.AdminRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AdminService;
import com.example.avito.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final static String USER_NOT_FOUND = "user not found";
    private final static String USER_ROLE_NOT_FOUND = "user role not found";
    private final static String ADMIN_ROLE_NOT_FOUND = "admin role not found";
    
    @Override
    public List<User> getAllUsers() {
        return adminRepository.findAll();
    }

    @Override
    public ResponseEntity<?> setAdminRole(UUID id) {
        if(roleService.getAdminRole().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ADMIN_ROLE_NOT_FOUND));
        }
        Optional<User> userOptional = adminRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().clear();
            user.getRoles().add(roleService.getAdminRole().get());
            adminRepository.save(user);
            return ResponseEntity.ok().body(String.format("Роль администратора установлена для пользователя с email: %s, id: %d", user.getEmail(), user.getId()));
        } else {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> setUserRole(UUID id) {
        Optional<User> userOptional = adminRepository.findById(id);
        if(roleService.getUserRole().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_ROLE_NOT_FOUND));
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().clear();
            user.getRoles().add(roleService.getUserRole().get());
            adminRepository.save(user);
            return ResponseEntity.ok().body(String.format("Роль юзера установлена для пользователя с email: %s, id: %d", user.getEmail(), user.getId()));
        } else {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
    }
    public ResponseEntity<?> deleteUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok().body("deleted");
        } else {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
    }
}
