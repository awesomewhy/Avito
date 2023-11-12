package com.example.avito.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductShowDto {
    private String city;
    private BigDecimal price;
    private String type;
    private String description;
}
