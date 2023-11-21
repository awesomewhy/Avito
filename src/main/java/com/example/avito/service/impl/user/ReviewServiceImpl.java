package com.example.avito.service.impl.user;

import com.example.avito.dto.review.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.repository.ReviewRepository;
import com.example.avito.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    @Override
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = new Review();
        review.setType(reviewDto.getType());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok().body(savedReview);
    }
}
