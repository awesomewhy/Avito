package com.example.avito.controller;

import com.example.avito.dto.MyProductDto;
import com.example.avito.dto.ProductSellDto;
import com.example.avito.dto.ProductShowDto;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final AdminService adminService;

    @GetMapping("/myproducts")
    public List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email) {
//        List<Product> products = productService.getMyProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getMyProducts(email);
    }

    @GetMapping()
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
