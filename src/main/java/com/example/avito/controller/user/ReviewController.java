package com.example.avito.controller.user;

import com.example.avito.dto.reviewdto.ReviewDto;
import com.example.avito.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createReview(@PathVariable String id, @RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(id, reviewDto);
    }
    @GetMapping("/average")
    public ResponseEntity<?> getAverageRating() {
        return reviewService.getAverageRating();
    }
}
