package com.example.avito.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDto {

    private Long idCreator;
    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;

}
