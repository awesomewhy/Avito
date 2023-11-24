package com.example.avito.dto.productdto;

import com.example.avito.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSellDto {
    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User creatorId;
    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;
}
