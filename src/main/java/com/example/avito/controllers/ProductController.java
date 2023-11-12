package com.example.avito.controllers;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final AdminService adminService;
    @GetMapping("/myproducts")
    public List<MyProductDto> getMyProducts() {
//        List<Product> products = productService.getMyProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getMyProducts();
    }

    @GetMapping("/products")
    public String getProducts() {
        List<Product> products = productService.getAllProducts();
        return adminService.convertObjectsToJson(products);
    }

    @PostMapping("/additem")
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.addItem(productDto);
    }

    @PostMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
