package com.example.avito.service.impl.user;

import com.example.avito.dto.reviewdto.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.enums.ReviewServiceError;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.mapper.ReviewMapper;
import com.example.avito.repository.ReviewRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ReviewService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    private final static String COMMENT_NOT_ADDED = "comment not added";
    private final static String COMMENT_ADDED = "comment added";
    private final static String USER_NOT_FOUND = "user not found";

    @Override
    public ResponseEntity<?> createReview(@PathVariable String uuid, @RequestBody ReviewDto reviewDto) {
        try {
            Optional<User> reviewer = userService.getAuthenticationPrincipalUserByEmail();
            if (reviewer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, ReviewServiceError.USER_NOT_FOUND.getMessage()
                        + ReviewServiceError.COMMENT_NOT_ADDED.getMessage()));
            }
            Optional<User> user = userRepository.findById(UUID.fromString(uuid));
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, ReviewServiceError.USER_NOT_FOUND.getMessage()
                        + ReviewServiceError.COMMENT_NOT_ADDED.getMessage()));
            }
            Review review = Review.builder()
                    .userId(user.get())
                    .reviewerId(reviewer.get())
                    .type(reviewDto.getType())
                    .rating(reviewDto.getRating())
                    .comment(reviewDto.getComment())
                    .build();
            reviewRepository.save(review);
            return ResponseEntity.ok().body(COMMENT_ADDED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "неккоректный uuid"));
        }

    }

    @Override
    public ResponseEntity<?> getMyReviews() {
        Optional<User> user = userService.getAuthenticationPrincipalUserByEmail();
        if (user.isPresent()) {
            List<Review> reviews = reviewRepository.findByUserId(user.get());
            List<ReviewDto> reviewDtos = reviewMapper.mapToMyRewiewsDto(reviews);
            return ResponseEntity.ok().body(reviewDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, USER_NOT_FOUND));
        }
    }

    @Override
    public ResponseEntity<?> getAverageRating() {
        Optional<User> user = userService.getAuthenticationPrincipalUserByEmail();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, USER_NOT_FOUND));
        }
        List<BigDecimal> ratings = reviewRepository.findByUserId(user.get()).stream()
                .map(Review::getRating)
                .toList();

        if (ratings.isEmpty()) {
            return ResponseEntity.ok().body(BigDecimal.ZERO);
        }

        BigDecimal averageRating = ratings.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(ratings.size()), 1, RoundingMode.HALF_UP);

        return ResponseEntity.ok().body(averageRating);
    }
}

