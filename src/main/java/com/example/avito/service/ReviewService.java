package com.example.avito.service;

import com.example.avito.dto.review.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReviewService {
    ResponseEntity<?> createReview(@PathVariable String uuid, @RequestBody ReviewDto reviewDto);
    ResponseEntity<?> getMyReviews();
    ResponseEntity<?> getAverageRating();
}
