//package com.example.avito.controllers;
//
//import com.example.avito.entity.Product;
//import com.example.avito.entity.User;
//import com.example.avito.service.AdminService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AdminControllerTest {
//    @Mock
//    private AdminService adminService;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    @DisplayName("GET /users возвращает всех пользователей в json формате")
//    public void getAllUsers_testConvertObjectsToJsonWhenProductListThenJsonString() throws Exception {
//        List<Product> products = Arrays.asList(new Product(), new Product());
//        String expectedJson = objectMapper.writeValueAsString(products);
//
//        when(adminService.convertObjectsToJson(products)).thenReturn(expectedJson);
//
//        String actualJson = adminService.convertObjectsToJson(products);
//
//        assertEquals(expectedJson, actualJson);
//    }
//
//    @Test
//    public void getAllUsers_testConvertObjectsToJsonWhenUserListThenJsonString() throws Exception {
//        List<User> users = Arrays.asList(new User(), new User());
//        String expectedJson = objectMapper.writeValueAsString(users);
//
//        when(adminService.convertObjectsToJson(users)).thenReturn(expectedJson);
//
//        String actualJson = adminService.convertObjectsToJson(users);
//
//        assertEquals(expectedJson, actualJson);
//    }
//
//    @Test
//    public void getAllUsers_testConvertObjectsToJsonWhenEmptyListThenJsonString() throws Exception {
//        List<Object> emptyList = Collections.emptyList();
//        String expectedJson = objectMapper.writeValueAsString(emptyList);
//
//        when(adminService.convertObjectsToJson(emptyList)).thenReturn(expectedJson);
//
//        String actualJson = adminService.convertObjectsToJson(emptyList);
//
//        assertEquals(expectedJson, actualJson);
//    }
//
//    @Test
//    public void getAllUsers_testConvertObjectsToJsonWhenNullListThenJsonString() throws Exception {
//        List<Object> nullList = null;
//        String expectedJson = objectMapper.writeValueAsString(nullList);
//
//        when(adminService.convertObjectsToJson(nullList)).thenReturn(expectedJson);
//
//        String actualJson = adminService.convertObjectsToJson(nullList);
//
//        assertEquals(expectedJson, actualJson);
//    }
//}