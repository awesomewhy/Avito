package com.example.avito.service.impl;

import com.example.avito.dtos.RegistartionUserDto;
import com.example.avito.entity.Role;
import com.example.avito.entity.User;
import com.example.avito.repository.RoleRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.beans.Transient;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllPersons() {
        return userRepository.findAll();
    }

    @Override
    public User getUserIdByEmail(String email) {
        return userRepository.getUserIdByEmail(email);
    }

    @Override
    @Transactional
    public User updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        User updateUser = getUserIdByEmail(email);
        updateUser.setUsername(user.getUsername());
        updateUser.setNickname(user.getNickname());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setEmail(user.getEmail());
        updateUser.setCity(user.getCity());

        return userRepository.save(updateUser);
    }

    @Override
    public User getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        return getUserIdByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Override
    public User createNewUser(RegistartionUserDto registartionUserDto) {
        User user = new User();
        user.setUsername(registartionUserDto.getUsername());
        user.setEmail(registartionUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registartionUserDto.getPassword()));
        user.setCity(registartionUserDto.getCity());
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }




}
