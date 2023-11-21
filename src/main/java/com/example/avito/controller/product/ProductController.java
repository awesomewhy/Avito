package com.example.avito.controller.product;

import com.example.avito.dto.otherdto.PriceSortDto;
import com.example.avito.dto.productdto.MyProductDto;
import com.example.avito.dto.productdto.ProductSellDto;
import com.example.avito.dto.productdto.ProductShowDto;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AdminService adminService;

    @GetMapping("/myproducts")
    public List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email) {
//        List<Product> products = productService.getMyProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getMyProducts(email);
    }

    @GetMapping("/")
    public List<ProductShowDto> getAllProducts() {
//        List<ProductShowDto> products = productService.getAllProducts();
//        return adminService.convertObjectsToJson(products);

        return productService.getAllProducts();
    }

    @PostMapping("/additem")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal String email, @RequestBody ProductSellDto productDto) {
        return productService.addItem(email, productDto);
    }

    @DeleteMapping("/profile/deleteproduct/{id}")
    public ResponseEntity<?> deleteProductById(@AuthenticationPrincipal String email, @PathVariable Long id) {
        return productService.deleteProductById(email, id);
    }

    @GetMapping("/sortbycity")
    public List<ProductShowDto> sortByCity() {
        return productService.sortProductsByCity();
    }

    @GetMapping("/sortbyprice")
    public List<ProductShowDto> sortByPrice(@RequestBody PriceSortDto priceSortDto) throws Exception {
        return productService.sortByPrice(priceSortDto);
    }
}