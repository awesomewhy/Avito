package com.example.avito.service.impl;

import com.example.avito.dto.PriceSortDto;
import com.example.avito.dto.ProductSellDto;
import com.example.avito.dto.ProductShowDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.mapper.ProductMapper;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private User user;
    private Product product;
    private ProductSellDto productDto;

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

        productDto = new ProductSellDto();
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
        assertEquals("Продукт успешно добавлен", response.getBody());
    }

    @Test
    public void testDeleteProductByIdWhenProductAndUserExistAndUserIsCreatorThenProductDeleted() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        productService.deleteProductById(user.getEmail(), product.getId());

        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    public void testSortByPriceWhenStartAndEndPricesProvidedThenReturnList() throws Exception {
        PriceSortDto priceSortDto = new PriceSortDto();
        priceSortDto.setStartPrice(new BigDecimal("50.00"));
        priceSortDto.setEndPrice(new BigDecimal("150.00"));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(productMapper.mapToProductShowDto(product)).thenReturn(new ProductShowDto());

        List<ProductShowDto> productShowDtos = productService.sortByPrice(priceSortDto);

        assertEquals(1, productShowDtos.size());
    }

    @Test
    public void testSortByPriceWhenStartAndEndPricesNotProvidedThenThrowException() {
        PriceSortDto priceSortDto = new PriceSortDto();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.sortByPrice(priceSortDto));

        assertEquals("Необходимо указать начальную и конечную цену", exception.getMessage());
    }
}
