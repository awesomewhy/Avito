package com.example.avito.service.impl.user;

import com.example.avito.dto.reviewdto.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.enums.ReviewServiceError;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.mapper.ReviewMapper;
import com.example.avito.repository.ReviewRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

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
    void testCreateReviewWhenReviewerNotFoundThenReturnNotFound() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewService.createReview(mockUser.getId().toString(), mockReviewDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertThat(errorResponse.getMessage()).contains(ReviewServiceError.USER_NOT_FOUND.getMessage());
        assertThat(errorResponse.getMessage()).contains(ReviewServiceError.COMMENT_NOT_ADDED.getMessage());
        verify(userRepository, never()).findById(any(UUID.class));
    }

    @Test
    void testCreateReviewWhenUserNotFoundThenReturnNotFound() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewService.createReview(mockUser.getId().toString(), mockReviewDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertThat(errorResponse.getMessage()).contains(ReviewServiceError.USER_NOT_FOUND.getMessage());
        assertThat(errorResponse.getMessage()).contains(ReviewServiceError.COMMENT_NOT_ADDED.getMessage());
    }

    @Test
    void testCreateReviewWhenRequestIsValidThenReturnOk() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockUser));

        ResponseEntity<?> response = reviewService.createReview(mockUser.getId().toString(), mockReviewDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(ReviewServiceError.COMMENT_ADDED.getMessage());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void testGetMyReviewsWhenUserNotFoundThenReturnNotFound() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewService.getMyReviews();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertThat(errorResponse.getMessage()).isEqualTo(ReviewServiceError.USER_NOT_FOUND.getMessage());
    }

    @Test
    void testGetMyReviewsWhenUserHasReviewsThenReturnOk() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(reviewRepository.findByUserId(mockUser)).thenReturn(List.of(mockReview));
        when(reviewMapper.mapToMyRewiewsDto(List.of(mockReview))).thenReturn(List.of(mockReviewDto));

        ResponseEntity<?> response = reviewService.getMyReviews();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(mockReviewDto));
    }

    @Test
    void testGetMyReviewsWhenUserHasNoReviewsThenReturnOk() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(reviewRepository.findByUserId(mockUser)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = reviewService.getMyReviews();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAverageRatingWhenUserNotFoundThenReturnNotFound() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewService.getAverageRating();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertThat(errorResponse.getMessage()).isEqualTo(ReviewServiceError.USER_NOT_FOUND.getMessage());
    }

    @Test
    void testGetAverageRatingWhenUserHasReviewsThenReturnAverageRating() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(reviewRepository.findByUserId(mockUser)).thenReturn(List.of(mockReview));

        ResponseEntity<?> response = reviewService.getAverageRating();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(BigDecimal.TEN.setScale(1, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    void testGetAverageRatingWhenUserHasNoReviewsThenReturnZeroRating() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));
        when(reviewRepository.findByUserId(mockUser)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = reviewService.getAverageRating();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(BigDecimal.ZERO);
    }
}