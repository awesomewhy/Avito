//package com.example.avito.controllers;
//
//import com.example.avito.dtos.JwtRequest;
//import com.example.avito.dtos.RegistartionUserDto;
//import com.example.avito.service.AuthService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthService authService;
//
//    @Test
//    public void testCreateAuthTokenWhenValidJwtRequestThenReturnResponseEntity() throws Exception {
//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setId(1L);
//        jwtRequest.setEmail("test@example.com");
//        jwtRequest.setPassword("password");
//
//        when(authService.createAuthToken(any(JwtRequest.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(jwtRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
//                        .contentType("application/json")
//                        .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateAuthTokenWhenInvalidJwtRequestThenReturnBadRequest() throws Exception {
//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setId(1L);
//        jwtRequest.setEmail("test@example.com");
//        jwtRequest.setPassword("password");
//
//        when(authService.createAuthToken(any(JwtRequest.class))).thenThrow(new IllegalArgumentException());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(jwtRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
//                        .contentType("application/json")
//                        .content(json))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void testCreateNewUserWhenValidRegistartionUserDtoThenReturnResponseEntity() throws Exception {
//        RegistartionUserDto registartionUserDto = new RegistartionUserDto();
//        registartionUserDto.setUsername("test");
//        registartionUserDto.setPassword("password");
//        registartionUserDto.setConfirmPassword("password");
//        registartionUserDto.setEmail("test@example.com");
//        registartionUserDto.setCity("testCity");
//
//        when(authService.createNewUser(any(RegistartionUserDto.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(registartionUserDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
//                        .contentType("application/json")
//                        .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateNewUserWhenInvalidRegistartionUserDtoThenReturnBadRequest() throws Exception {
//        RegistartionUserDto registartionUserDto = new RegistartionUserDto();
//        registartionUserDto.setUsername("test");
//        registartionUserDto.setPassword("password");
//        registartionUserDto.setConfirmPassword("password");
//        registartionUserDto.setEmail("test@example.com");
//        registartionUserDto.setCity("testCity");
//
//        when(authService.createNewUser(any(RegistartionUserDto.class))).thenThrow(new IllegalArgumentException());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(registartionUserDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
//                        .contentType("application/json")
//                        .content(json))
//                .andExpect(status().isBadRequest());
//    }
//}