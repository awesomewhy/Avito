package com.example.avito.service.impl;

import com.example.avito.dto.productdto.ProductSellDto;
import com.example.avito.dto.productdto.ProductShowDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.mapper.ProductMapper;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.impl.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemWhenUserExistsThenReturnCreated() {
        String email = "test@test.com";
        ProductSellDto productDto = new ProductSellDto();
        productDto.setPrice(BigDecimal.valueOf(100));
        productDto.setType("type");
        productDto.setCity("city");
        productDto.setDateCreation(new Date());
        productDto.setDescription("description");

        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());
        when(productMapper.mapToProductShowDto(any(Product.class))).thenReturn(new ProductShowDto());

        ResponseEntity<?> response = productService.addItem(productDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddItemWhenUserDoesNotExistThenReturnBadRequest() {
        String email = "test@test.com";
        ProductSellDto productDto = new ProductSellDto();
        productDto.setPrice(BigDecimal.valueOf(100));
        productDto.setType("type");
        productDto.setCity("city");
        productDto.setDateCreation(new Date());
        productDto.setDescription("description");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.addItem(productDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAllProductsWhenProductsExistThenReturnProductShowDtoList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.mapToProductShowDto(any(Product.class))).thenReturn(new ProductShowDto());

        List<ProductShowDto> result = productService.getAllProducts();

        assertEquals(products.size(), result.size());
    }

    @Test
    void testSortProductsByCityWhenProductsExistThenReturnProductShowDtoList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.mapToProductShowDto(any(Product.class))).thenReturn(new ProductShowDto());

        List<ProductShowDto> result = productService.sortProductsByCity();

        assertEquals(products.size(), result.size());
    }
}
