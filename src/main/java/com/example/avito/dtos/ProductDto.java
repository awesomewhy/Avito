package com.example.avito.dtos;

import com.example.avito.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private User idCreator;
    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;
}
