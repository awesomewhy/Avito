package com.example.avito.service.impl.product;

import com.example.avito.dto.productdto.ProductSellDto;
import com.example.avito.dto.reviewdto.ReviewDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.Review;
import com.example.avito.entity.User;
import com.example.avito.mapper.ProductMapper;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private User mockUser;
    private ReviewDto mockReviewDto;
    private Review mockReview;

    private ProductSellDto productSellDto;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testUser");

        productSellDto = ProductSellDto.builder()
                .creatorId(mockUser)
                .price(BigDecimal.TEN)
                .type("car")
                .city(mockUser.getCity())
                .dateCreation(new Date())
                .description("test")
                .build();
    }

    @Test
    void testAddItemWhenProductAddReturnOk() {
        when(userService.getAuthenticationPrincipalUserByEmail()).thenReturn(Optional.of(mockUser));

        ResponseEntity<?> response = productService.addItem(productSellDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Продукт успешно добавлен");
    }
}