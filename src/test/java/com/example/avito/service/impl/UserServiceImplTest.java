package com.example.avito.service.impl;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.MyProfileDto;
import com.example.avito.dto.userdto.UpdateProfileDto;
import com.example.avito.entity.Role;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import com.example.avito.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(1, "USER");
        user = User.builder()
                .id(UUID.fromString(UUID.randomUUID().toString()))
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("password")
                .city("testCity")
                .roles(Collections.singletonList(role))
                .build();
    }

    @Test
    @DisplayName("testUpdateUserWhenUserDoesNotExistThenReturnBadRequest")
    void testUpdateUserWhenUserDoesNotExistThenReturnBadRequest() {
        UpdateProfileDto updateUserDto = UpdateProfileDto.builder()
                .username("newTest")
                .nickname("newTest")
                .email("newTest@test.com")
                .city("newTestCity")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.updateUser("test@test.com", updateUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("user not found", response.getBody());
    }

    @Test
    @DisplayName("testGetMyProfileWhenUserExistsThenReturnMyProfileDto")
    void testGetMyProfileWhenUserExistsThenReturnMyProfileDto() {
        String email = "test@test.com";
        User user = User.builder()
                .username("testUser")
                .nickname("testNickname")
                .email(email)
                .city("testCity")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, passwordEncoder);

        ResponseEntity<?> response = userService.getMyProfile(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof MyProfileDto);
        MyProfileDto myProfileDto = (MyProfileDto) response.getBody();
        assertEquals(user.getUsername(), myProfileDto.getUsername());
        assertEquals(user.getNickname(), myProfileDto.getNickname());
        assertEquals(user.getEmail(), myProfileDto.getEmail());
        assertEquals(user.getCity(), myProfileDto.getCity());
    }

    @Test
    @DisplayName("testGetMyProfileWhenUserDoesNotExistThenReturnNotFound")
    void testGetMyProfileWhenUserDoesNotExistThenReturnNotFound() {
        String email = "test@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, passwordEncoder);

        ResponseEntity<?> response = userService.getMyProfile(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("testLoadUserByUsernameWhenUserExistsThenReturnUserDetails")
    void testLoadUserByUsernameWhenUserExistsThenReturnUserDetails() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role.getName())));
    }

    @Test
    @DisplayName("testLoadUserByUsernameWhenUserDoesNotExistThenThrowUsernameNotFoundException")
    void testLoadUserByUsernameWhenUserDoesNotExistThenThrowUsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@test.com"));
    }

    @Test
    @DisplayName("testChangePasswordWhenOldPasswordIsCorrectAndNewPasswordIsDifferentThenReturnOk")
    void testChangePasswordWhenOldPasswordIsCorrectAndNewPasswordIsDifferentThenReturnOk() {
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("password")
                .newPassword("qweasd")
                .repeatPassword("qweasd")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true, false);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userService.changePassword("test@test.com", changePasswordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Пароль успешно изменен", response.getBody());
    }

    @Test
    @DisplayName("testChangePasswordWhenNewPasswordIsSameAsOldPasswordThenReturnBadRequest")
    void testChangePasswordWhenNewPasswordIsSameAsOldPasswordThenReturnBadRequest() {
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("password")
                .newPassword("qweasd")
                .repeatPassword("qweasd")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = userService.changePassword("test@test.com", changePasswordDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Пароль не должен совпадать со старым", response.getBody());
    }

    @Test
    @DisplayName("testDeleteProfileWhenPasswordIsCorrectThenReturnOk")
    void testDeleteProfileWhenPasswordIsCorrectThenReturnOk() {
        DeleteProfileDto deleteProfileDto = new DeleteProfileDto();
        deleteProfileDto.setPassword("password");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        doNothing().when(userRepository).delete(any(User.class));

        ResponseEntity<?> response = userService.deleteProfile("test@test.com", deleteProfileDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Профиль успешно удален", response.getBody());
    }

    @Test
    @DisplayName("testDeleteProfileWhenPasswordIsIncorrectThenReturnBadRequest")
    void testDeleteProfileWhenPasswordIsIncorrectThenReturnBadRequest() {
        DeleteProfileDto deleteProfileDto = new DeleteProfileDto();
        deleteProfileDto.setPassword("incorrectPassword");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = userService.deleteProfile("test@test.com", deleteProfileDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ErrorResponse(401,"Профиль не удален, неверный пароль"), response.getBody());
    }
}