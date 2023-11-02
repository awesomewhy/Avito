package com.example.avito.service;

import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService{
    Product addItem(@RequestBody ProductDto productDto);

    List<Product> getAllProducts();
}
