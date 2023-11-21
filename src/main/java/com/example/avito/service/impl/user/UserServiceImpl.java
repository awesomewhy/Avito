package com.example.avito.service.impl.user;

import com.example.avito.dto.userdto.*;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_BY_EMAIL = "user with email %s not found";
    private final static String USER_WHIT_THIS_EMAIL_EXIST = "user with this email exist";
    private final static String USER_NOT_FOUND = "user not found";
    private final static String PASSWORD_MATCHED = "Пароль не должен совпадать со старым";
    private final static String BAD_PASSWORD = "неверно введен пароль";
    private final static String USER_SAVED = "Пользователь сохранен";
    private final static String PASSWORD_CHANGED_SUCCESSFULLY = "Пароль успешно изменен";
    private final static String OLD_PASSWORD_NOT_MATCH = "Старый пароль не совпадает";
    private final static String PROFILE_DELETED_SUCCESSFULLY = "Профиль успешно удален";
    private final static String PROFILE_NOT_DELETED = "Профиль не удален, неверный пароль";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String email, @RequestBody UpdateProfileDto updateUserDto) {
        CompletableFuture<ResponseEntity<?>> future = CompletableFuture.supplyAsync(() -> {
            Optional<User> updateUser = userRepository.findByEmail(email);
            if (updateUser.isPresent()) {
                if (userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
                    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), USER_WHIT_THIS_EMAIL_EXIST), HttpStatus.BAD_REQUEST);
                }

                User user = updateUser.get();
                user.setUsername(updateUserDto.getUsername());
                user.setNickname(updateUserDto.getNickname());
                user.setEmail(updateUserDto.getEmail());
                user.setCity(updateUserDto.getCity());
                userRepository.save(user);

                return ResponseEntity.ok().body(USER_SAVED);
            } else {
                return ResponseEntity.badRequest().body(USER_NOT_FOUND);
            }
        });

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Запрос слишком долгий");
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }
    @Override
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            MyProfileDto myProfileDto = new MyProfileDto();
            myProfileDto.setUsername(user.getUsername());
            myProfileDto.setNickname(user.getNickname());
            myProfileDto.setEmail(user.getEmail());
            myProfileDto.setCity(user.getCity());
            return ResponseEntity.ok(myProfileDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
    public User createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        User user = User.builder()
                .username(registrationUserDto.getUsername())
                    .email(registrationUserDto.getEmail())
                        .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                            .city(registrationUserDto.getCity())
                                .roles(List.of(roleService.getUserRole())).build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangePasswordDto changePasswordDto) {
        Optional<User> updateUser = userRepository.findByEmail(email);

        if (updateUser.isPresent() && passwordEncoder.matches(changePasswordDto.getOldPassword(), updateUser.get().getPassword())) {
            if(passwordEncoder.matches(changePasswordDto.getNewPassword(), updateUser.get().getPassword())) {
                return ResponseEntity.badRequest().body(PASSWORD_MATCHED);
            }
            if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatPassword())) {
                return ResponseEntity.badRequest().body(BAD_PASSWORD);
            }
            User user = updateUser.get();
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body(PASSWORD_CHANGED_SUCCESSFULLY);
        } else {
            return ResponseEntity.badRequest().body(OLD_PASSWORD_NOT_MATCH);
        }
    }
    @Override
    @Transactional
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal String email, @RequestBody DeleteProfileDto deleteProfileDto) {
        Optional<User> deleteUser = userRepository.findByEmail(email);

        if (deleteUser.isPresent() && passwordEncoder.matches(deleteProfileDto.getPassword(), deleteUser.get().getPassword())) {
            userRepository.delete(deleteUser.get());
            return ResponseEntity.ok().body(PROFILE_DELETED_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PROFILE_NOT_DELETED);
        }
    }
}
