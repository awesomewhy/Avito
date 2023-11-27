package com.example.avito.service;

import com.example.avito.dto.productdto.MyProductDto;
import com.example.avito.dto.otherdto.PriceSortDto;
import com.example.avito.dto.productdto.ProductSellDto;
import com.example.avito.dto.productdto.ProductShowDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService{
    ResponseEntity<?> addItem(@RequestBody ProductSellDto productDto);
    List<ProductShowDto> getAllProducts();
    List<ProductShowDto> sortProductsByCity();
    List<ProductShowDto> sortByPrice(@RequestBody PriceSortDto priceSortDto) throws Exception;
    ResponseEntity<?> deleteProductById(Long id);
    ResponseEntity<?> getMyProducts();
}
