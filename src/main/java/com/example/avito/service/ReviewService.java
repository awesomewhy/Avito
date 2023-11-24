package com.example.avito.service;

import com.example.avito.dto.reviewdto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewService {
    ResponseEntity<?> createReview(@PathVariable String uuid, @RequestBody ReviewDto reviewDto);
    ResponseEntity<?> getMyReviews();
    ResponseEntity<?> getAverageRating();
}
