package com.example.avito.service.impl.user;

import com.example.avito.dto.review.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.mapper.ReviewMapper;
import com.example.avito.repository.ReviewRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    private final static String COMMENT_NOT_ADDED = "комментарий не поставлен";
    @Override
    public ResponseEntity<?> createReview(@PathVariable String uuid, @AuthenticationPrincipal String email, @RequestBody ReviewDto reviewDto) {
        Optional<User> reviewer = userRepository.findByEmail(email);
        if (reviewer.isPresent()) {
            Optional<User> user = userRepository.findById(UUID.fromString(uuid));
            if (user.isPresent()) {
                Review review = new Review();
                review.setUserId(user.get());
                review.setReviewerId(reviewer.get());
                review.setType(reviewDto.getType());
                review.setRating(reviewDto.getRating());
                review.setComment(reviewDto.getComment());
                reviewRepository.save(review);
                return ResponseEntity.ok().body("comment added");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, COMMENT_NOT_ADDED));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, COMMENT_NOT_ADDED));
        }
    }

    @Override
    public ResponseEntity<?> getMyReviews(@AuthenticationPrincipal String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Review> reviews = reviewRepository.findByUserId(user.get());
            List<ReviewDto> reviewDtos= reviewMapper.mapToMyRewiewsDto(reviews);
            return ResponseEntity.ok().body(reviewDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "User not found"));
        }
    }
}
