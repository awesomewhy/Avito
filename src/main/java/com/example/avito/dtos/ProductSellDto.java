package com.example.avito.dtos;

import com.example.avito.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSellDto {
    private User idCreator;
    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;
}
