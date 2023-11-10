package com.example.avito.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long idCreator;
    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;
}
