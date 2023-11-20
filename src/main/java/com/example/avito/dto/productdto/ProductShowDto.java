package com.example.avito.dto.productdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductShowDto {
    private String description;
    private BigDecimal price;
    private String city;
}
