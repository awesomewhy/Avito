package com.example.avito.controller.user;

import com.example.avito.dto.otherdto.PriceSortDto;
import com.example.avito.dto.productdto.ProductShowDto;
import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.UpdateUserDto;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal String email) {
        return userService.getMyProfile(email);
    }


}
