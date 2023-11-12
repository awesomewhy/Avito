package com.example.avito.dtos;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PriceSortDto {
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
