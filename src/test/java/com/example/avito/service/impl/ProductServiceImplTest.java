package com.example.avito.service.impl;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private User user;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        product = new Product();
        product.setId(1L);
        product.setIdCreator(user);
        product.setPrice(new BigDecimal("100.00"));
        product.setType("test");
        product.setCity("test city");
        product.setDateCreation(new Date());
        product.setDescription("test description");

        productDto = new ProductDto();
        productDto.setIdCreator(user);
        productDto.setPrice(new BigDecimal("100.00"));
        productDto.setType("test");
        productDto.setCity("test city");
        productDto.setDateCreation(new Date());
        productDto.setDescription("test description");
    }

    @Test
    public void testAddItemWhenProductAddedThenSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = productService.addItem(user.getEmail(), productDto);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals("продукт успешно добавлен", response.getBody());
    }

    @Test
    public void testGetAllProductsWhenProductsExistThenReturnList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    public void testDeleteProductByIdWhenProductAndUserExistAndUserIsCreatorThenProductDeleted() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        productService.deleteProductById(user.getEmail(), product.getId());

        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    public void testGetMyProductsWhenUserExistsAndHasProductsThenReturnList() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findAllByIdCreator(user)).thenReturn(Arrays.asList(product));

        List<MyProductDto> myProductDtos = productService.getMyProducts(user.getEmail());

        assertEquals(1, myProductDtos.size());
        assertEquals(product.getCity(), myProductDtos.get(0).getCity());
        assertEquals(product.getType(), myProductDtos.get(0).getType());
        assertEquals(product.getPrice(), myProductDtos.get(0).getPrice());
        assertEquals(String.valueOf(product.getDateCreation()), myProductDtos.get(0).getDateCreation());
        assertEquals(product.getDescription(), myProductDtos.get(0).getDescription());
    }
}
