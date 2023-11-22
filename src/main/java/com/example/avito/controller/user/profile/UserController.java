package com.example.avito.controller.user.profile;

import com.example.avito.service.ReviewService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile() {
        return userService.getMyProfile();
    }

    @GetMapping("/profile/reviews")
    public ResponseEntity<?> getMyReviews() {
        return reviewService.getMyReviews();
    }
}
