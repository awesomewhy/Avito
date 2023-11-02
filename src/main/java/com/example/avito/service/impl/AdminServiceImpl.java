package com.example.avito.service.impl;

import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
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
}
