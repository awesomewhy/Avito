package com.example.avito.dto.productdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyProductDto {
    private String city;
    private String type;
    private BigDecimal price;
    private String dateCreation;
    private String description;
}
