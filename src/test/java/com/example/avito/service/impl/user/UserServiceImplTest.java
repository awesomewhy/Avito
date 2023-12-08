package com.example.avito.service.impl.user;

import com.example.avito.dto.reviewdto.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private ReviewDto mockReviewDto;
    private Review mockReview;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testUser");

        mockReviewDto = new ReviewDto();
        mockReviewDto.setUsername("reviewer");
        mockReviewDto.setType("positive");
        mockReviewDto.setRating(BigDecimal.TEN);
        mockReviewDto.setComment("Great service!");

        mockReview = new Review();
        mockReview.setUserId(mockUser);
        mockReview.setReviewerId(mockUser);
        mockReview.setType(mockReviewDto.getType());
        mockReview.setRating(mockReviewDto.getRating());
        mockReview.setComment(mockReviewDto.getComment());
    }

    @Test
    void testGetAuthenticationPrincipalUserByEmailWhenUserIsFoundThenReturnUser() {

        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setEmail("user@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expectedUser));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(expectedUser.getEmail());
        SecurityContextHolder.setContext(securityContext);

        Optional<User> actualUser = userService.getAuthenticationPrincipalUserByEmail();

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get()).isEqualTo(expectedUser);
    }

    @Test
    void testGetAuthenticationPrincipalUserByEmailWhenUserIsNotFoundThenReturnEmpty() {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("nonexistent@example.com");
        SecurityContextHolder.setContext(securityContext);

        Optional<User> actualUser = userService.getAuthenticationPrincipalUserByEmail();

        assertThat(actualUser).isNotPresent();
    }

    @Test
    void testUpdateUserWhenUserIsFoundThenReturnResponseEntityOk() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));

    }

    @Test
    void updateUserEmail() {
    }

    @Test
    void getMyProfile() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void createNewUser() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteProfile() {
    }
}