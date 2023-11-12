package com.example.avito.service;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.PriceSortDto;
import com.example.avito.dtos.ProductSellDto;
import com.example.avito.dtos.ProductShowDto;
import com.example.avito.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService{
    ResponseEntity<?> addItem(@AuthenticationPrincipal String email, @RequestBody ProductSellDto productDto);
    List<Product> getAllProducts();
    List<ProductShowDto> sortProductsByCity();
    List<ProductShowDto> sortByPrice(@RequestBody PriceSortDto priceSortDto) throws Exception;
    ResponseEntity<?> deleteProductById(@AuthenticationPrincipal String email, Long id);
    List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email);
}
