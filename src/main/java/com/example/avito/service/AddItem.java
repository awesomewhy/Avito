package com.example.avito.service;

import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import org.springframework.web.bind.annotation.RequestBody;

public interface AddItem {
    Product save(@RequestBody ProductDto productDto);
}
