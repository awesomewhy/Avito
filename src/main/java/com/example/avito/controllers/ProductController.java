package com.example.avito.controllers;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductSellDto;
import com.example.avito.dtos.ProductShowDto;
import com.example.avito.entity.Product;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final AdminService adminService;
    @GetMapping("/myproducts")
    public List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email) {
//        List<Product> products = productService.getMyProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getMyProducts(email);
    }

    @GetMapping("/products")
    public List<ProductShowDto> getProducts() {
//        List<ProductShowDto> products = productService.getAllProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getAllProducts();
    }

    @PostMapping("/additem")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal String email, @RequestBody ProductSellDto productDto) {
        return productService.addItem(email, productDto);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal String email, @PathVariable Long id) {
        return productService.deleteProductById(email, id);
    }
}
