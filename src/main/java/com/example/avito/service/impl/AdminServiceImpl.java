package com.example.avito.service.impl;

import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.repository.AdminRepository;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;
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
    public List<Product> sortProductsByCity() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "city"));
    }
}
