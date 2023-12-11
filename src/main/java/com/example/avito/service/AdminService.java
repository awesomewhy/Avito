package com.example.avito.service;

import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AdminService {
//    <T> String convertObjectsToJson(List<T> objects);
    List<User> getAllUsers();
    ResponseEntity<?> setAdminRole(UUID id);
    ResponseEntity<?> setUserRole(UUID id);
    ResponseEntity<?> deleteUserById(UUID id);
}
