package com.example.avito.controller.user;

import com.example.avito.dto.review.ReviewDto;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ReviewController {
    public final ReviewService reviewService;
    @GetMapping("/")
    public ResponseEntity<?> getMyReviews(@AuthenticationPrincipal String email) {
        return reviewService.getMyReviews(email);
    }
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createReview(@PathVariable String id, @AuthenticationPrincipal String email, @RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(id, email, reviewDto);
    }
}
