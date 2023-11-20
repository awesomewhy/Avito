package com.example.avito.dto.otherdto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PriceSortDto {
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
