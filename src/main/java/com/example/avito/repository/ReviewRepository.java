package com.example.avito.repository;

import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(User user);
}
