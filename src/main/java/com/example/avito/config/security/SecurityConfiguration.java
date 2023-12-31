package com.example.avito.config.security;

import com.example.avito.config.JwtRequestFilter;
import com.example.avito.config.security.PasswordEncoderConfiguration;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    // USER
    private static final String LOGIN = "/auth/login";
    private static final String REGISTRATION = "/auth/registration";
    private static final String ADD_ITEM = "/additem";
    private static final String PROFILE = "/profile";
    private static final String MY_PRODUCTS = "/myproducts";
    private static final String ALL_PRODUCTS = "/";
    private static final String DELETE_PRODUCT = "/profile/safety/deleteproduct/{id}";
    private static final String UPDATE = "/profile/safety/update";
    private static final String CHANGE_PASSWORD = "/profile/safety/changepassword";
    private static final String DELETE_PROFILE = "/profile/safety/deleteprofile";

    // REVIEW
    private static final String GET_MY_REVIEWS = "/profile/reviews";
    private static final String AVERAGE = "/user/average";
    private static final String ADD_REVIEW = "/user/create/{id}";

    // ADMIN
    private static final String USERS = "/admin/users";
    private static final String SET_USER_ROLE = "/userrole/{id}";
    private static final String SET_ADMIN_ROLE = "/adminrole/{id}";


    private void sharedSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
    }

    @Bean
    public SecurityFilterChain securityFilterChainUsersAPI(HttpSecurity httpSecurity) throws Exception {
        sharedSecurityConfiguration(httpSecurity);
        httpSecurity
                .securityMatcher(ADD_ITEM, PROFILE, UPDATE, CHANGE_PASSWORD, DELETE_PROFILE,
                        MY_PRODUCTS, DELETE_PRODUCT, ALL_PRODUCTS, GET_MY_REVIEWS, ADD_REVIEW, AVERAGE)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainAdminsAPI(HttpSecurity httpSecurity) throws Exception {
        sharedSecurityConfiguration(httpSecurity);
        httpSecurity
                .securityMatcher(USERS, SET_ADMIN_ROLE, SET_USER_ROLE)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().hasRole("ADMIN");
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoderConfiguration.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
