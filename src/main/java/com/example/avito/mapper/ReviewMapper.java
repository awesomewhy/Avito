package com.example.avito.mapper;

import com.example.avito.dto.productdto.MyProductDto;
import com.example.avito.dto.review.ReviewDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ReviewMapper {
    public List<ReviewDto> mapToMyRewiewsDto(List<Review> reviews) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setUsername(review.getReviewerId().getUsername());
            reviewDto.setType(review.getType());
            reviewDto.setRating(review.getRating());
            reviewDto.setComment(review.getComment());

            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }
}
