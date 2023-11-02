package com.example.avito.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDto {

    private Long id_creator;
    private BigDecimal price;
    private String type;
    private String city;
    private Date date_creation;
    private Date description;

}
