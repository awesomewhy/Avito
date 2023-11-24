package com.example.avito.dto.reviewdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewDto {
    private String username;
    private String type;
    private BigDecimal rating;
    private String comment;
}
