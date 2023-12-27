package com.example.avito.service.impl.user;

import com.example.avito.dto.userdto.*;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import com.example.avito.service.UserService;
import com.example.avito.validation.Validation;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {
    private final static String USER_NOT_FOUND_BY_EMAIL = "user with email %s not found";
    private final static String USER_WHIT_THIS_EMAIL_EXIST = "user with this email exist";
    private final static String USER_NOT_FOUND = "user not found";
    private final static String PASSWORD_MATCHED = "password must not be the same as the old one";
    private final static String USER_SAVED = "User saved";
    private final static String EMAIL_NULL = "Email is null";
    private final static String PASSWORD_NULL = "password is null";
    private final static String INVALID_EMAIL = "Неверно введенная почта";
    private final static String BAD_REPEAT_PASSWORD = "bad repeat password";
    private final static String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully";
    private final static String OLD_PASSWORD_NOT_MATCH = "Old password doesn't match";
    private final static String PROFILE_DELETED_SUCCESSFULLY = "Profile deleted successfully";
    private final static String PROFILE_NOT_DELETED = "Profile not deleted, incorrect password";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getAuthenticationPrincipalUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        return userRepository.findByEmail(email);
    }
    
    @Override
    @Transactional
    public ResponseEntity<?> updateUser(@RequestBody UpdateProfileDto updateUserDto) {
        Optional<User> updateUser = getAuthenticationPrincipalUserByEmail();
        if (updateUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, USER_NOT_FOUND));
        }
        User user = getUser(updateUserDto, updateUser);

        userRepository.save(user);

        return ResponseEntity.ok().body(USER_SAVED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUserEmail(@RequestBody UpdateUserEmailDto updateUserEmailDto) {
        Optional<User> updateUser = getAuthenticationPrincipalUserByEmail();
        if (updateUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
        }
        if (StringUtils.isEmpty(updateUserEmailDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), EMAIL_NULL));
        }
        if (userRepository.findByEmail(updateUserEmailDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), USER_WHIT_THIS_EMAIL_EXIST));
        }
//        if(!Validation.isValidEmailAddress(updateUserEmailDto.getEmail())) {
//            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), INVALID_EMAIL), HttpStatus.BAD_REQUEST);
//        }
        User user = updateUser.get();
        user.setEmail(updateUserEmailDto.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok().body(USER_SAVED);
    }

    @Override
    public ResponseEntity<?> getMyProfile() {
        Optional<User> userOptional = getAuthenticationPrincipalUserByEmail();
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(getMyProfileDto(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL, email)
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (roleService.getUserRole().isEmpty()) {
            log.error("role not found");
            return;
        }
        User user = User.builder()
                .username(registrationUserDto.getUsername())
                .email(registrationUserDto.getEmail())
                .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                .city(registrationUserDto.getCity())
                .roles(List.of(roleService.getUserRole().get()))
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        Optional<User> updateUser = getAuthenticationPrincipalUserByEmail();

        if (updateUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
        }
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), updateUser.get().getPassword())) {
            if (StringUtils.isEmpty(changePasswordDto.getOldPassword())
                    || StringUtils.isEmpty(changePasswordDto.getNewPassword())
                    || StringUtils.isEmpty(changePasswordDto.getRepeatPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), PASSWORD_NULL));
            }
            if (passwordEncoder.matches(changePasswordDto.getNewPassword(), updateUser.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), PASSWORD_MATCHED));
            }
            if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_REPEAT_PASSWORD));
            }
            User user = updateUser.get();
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body(PASSWORD_CHANGED_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), OLD_PASSWORD_NOT_MATCH));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProfile(@RequestBody DeleteProfileDto deleteProfileDto) {
        Optional<User> deleteUser = getAuthenticationPrincipalUserByEmail();
        if (deleteUser.isPresent() && passwordEncoder.matches(deleteProfileDto.getPassword(), deleteUser.get().getPassword())) {
            userRepository.delete(deleteUser.get());
            return ResponseEntity.ok().body(PROFILE_DELETED_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), PROFILE_NOT_DELETED));
        }
    }

    private User getUser(UpdateProfileDto updateUserDto, Optional<User> updateUser) {
        User user = updateUser.get();
        user.setUsername(StringUtils.isEmpty(updateUserDto.getUsername()) ? user.getUsername() : updateUserDto.getUsername());
        user.setNickname(StringUtils.isEmpty(updateUserDto.getNickname()) ? user.getNickname() : updateUserDto.getNickname());
        user.setCity(StringUtils.isEmpty(updateUserDto.getCity()) ? user.getCity() : updateUserDto.getCity());
        return user;
    }

    private MyProfileDto getMyProfileDto(User user) {
        MyProfileDto myProfileDto = new MyProfileDto();
        myProfileDto.setUsername(user.getUsername());
        myProfileDto.setNickname(user.getNickname());
        myProfileDto.setEmail(user.getEmail());
        myProfileDto.setCity(user.getCity());
        return myProfileDto;
    }
}
