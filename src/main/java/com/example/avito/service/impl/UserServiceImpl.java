package com.example.avito.service.impl;

import com.example.avito.dtos.*;
import com.example.avito.entity.User;
import com.example.avito.exceptions.AppError;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String email, @RequestBody UpdateUserDto updateUserDto) {
        Optional<User> updateUser = userRepository.findByEmail(email);
        if (updateUser.isPresent()) {
            if (userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным email уже существует"), HttpStatus.BAD_REQUEST);
            }

            User user = updateUser.get();
            user.setUsername(updateUserDto.getUsername());
            user.setNickname(updateUserDto.getNickname());
            user.setEmail(updateUserDto.getEmail());
            user.setCity(updateUserDto.getCity());
            userRepository.save(user);

            return ResponseEntity.ok().body("Пользователь сохранен");
        } else {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }
    }

    @Override
    public Optional<MyProfileDto> getMyProfile(@AuthenticationPrincipal String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            MyProfileDto myProfileDto = new MyProfileDto();
            myProfileDto.setUsername(user.getUsername());
            myProfileDto.setNickname(user.getNickname());
            myProfileDto.setEmail(user.getEmail());
            myProfileDto.setCity(user.getCity());
            return Optional.of(myProfileDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Override
    public User createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setCity(registrationUserDto.getCity());
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangePasswordDto changePasswordDto) {
        Optional<User> updateUser = userRepository.findByEmail(email);

        if (updateUser.isPresent() && passwordEncoder.matches(changePasswordDto.getOldPassword(), updateUser.get().getPassword())) {
            if(passwordEncoder.matches(changePasswordDto.getNewPassword(), updateUser.get().getPassword())) {
                return ResponseEntity.badRequest().body("Пароль не должен совпадать со старым");
            }
            if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatPassword())) {
                return ResponseEntity.badRequest().body("неверно введен повторный пароль");
            }
            User user = updateUser.get();
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body("Пароль успешно изменен");
        } else {
            return ResponseEntity.badRequest().body("Старый пароль не совпадает");
        }
    }

    @Override
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal String email, @RequestBody DeleteProfileDto deleteProfileDto) {
        Optional<User> deleteUser = userRepository.findByEmail(email);

        if (deleteUser.isPresent() && passwordEncoder.matches(deleteUser.get().getPassword(), deleteProfileDto.getPassword())) {
            userRepository.delete(deleteUser.get());
            return ResponseEntity.ok().body("Профиль успешно удален");
        } else {
            return ResponseEntity.badRequest().body("Неверный пароль или профиль не найден");
        }
    }
}
