package com.example.avito.dto.review;

import lombok.Data;

@Data
public class ReviewDto {
    private String type;
    private Byte rating;
    private String comment;
}
