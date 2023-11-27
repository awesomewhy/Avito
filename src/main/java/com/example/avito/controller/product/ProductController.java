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

    @GetMapping("/myproducts")
    public ResponseEntity<?> getMyProducts() {
        return productService.getMyProducts();
    }

    @GetMapping("/")
    public List<ProductShowDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/additem")
    public ResponseEntity<?> addProduct(@RequestBody ProductSellDto productDto) {
        return productService.addItem(productDto);
    }

    @DeleteMapping("/profile/deleteproduct/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
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
