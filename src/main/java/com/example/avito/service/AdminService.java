package com.example.avito.service;

import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    <T> String convertObjectsToJson(List<T> objects);
    List<User> getAllUsers();
    ResponseEntity<?> setAdminRole(Long id);
    ResponseEntity<?> setUserRole(Long id);
}
