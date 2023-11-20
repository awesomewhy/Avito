package com.example.avito.service;

import com.example.avito.dto.review.ReviewDto;
import com.example.avito.entity.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewService {
    ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto);
}
