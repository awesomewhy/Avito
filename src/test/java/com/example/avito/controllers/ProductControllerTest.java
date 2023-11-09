//package com.example.avito.controllers;
//
//import com.example.avito.dtos.ProductDto;
//import com.example.avito.entity.Product;
//import com.example.avito.service.ProductService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProductControllerTest {
//    @Mock
//    ProductService productService;
//
//    private ObjectMapper objectMapper;
//    @BeforeEach
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
////    @Test
////    @DisplayName("GET /users возвращает всех пользователей в json формате")
////    public void getAllUsers_testConvertObjectsToJsonWhenProductListThenJsonString() throws Exception {
////        List<Product> products = Arrays.asList(new Product(), new Product());
////        String expectedJson = objectMapper.writeValueAsString(products);
////
////        when(adminService.convertObjectsToJson(products)).thenReturn(expectedJson);
////
////        String actualJson = adminService.convertObjectsToJson(products);
////
////        assertEquals(expectedJson, actualJson);
////    }
//
//    @Test
//    void getMyProducts_testGetMyProductsWhenProductsExistThenReturnList() throws Exception{
//        List<Product> products = Arrays.asList(new Product(), new Product());
//
//        when(productService.getMyProducts()).thenReturn(products);
//
//        List<Product> actualProducts = productService.getMyProducts();
//
//        assertEquals(actualProducts, products);
//    }
//
//    @Test
//    void testAddProductThenCallAddItem() {
//        ProductDto productDto = new ProductDto();
//        Product product = new Product();
//
//        when(productService.addItem(productDto)).thenReturn(product);
//
//        Product actualProduct = productService.addItem(productDto);
//
//        assertEquals(actualProduct, product);
//        verify(productService, times(1)).addItem(productDto);
//    }
//
//    @Test
//    void getMyProducts() throws Exception {
//        List<Product> products = Arrays.asList(new Product(), new Product());
//
//        when(productService.getMyProducts()).thenReturn(products);
//
//        List<Product> actualProducts = productService.getMyProducts();
//
//        assertEquals(actualProducts, products);
//    }
//
//    @Test
//    void deleteProduct_testDeleteProductWhenProductExistsThenTrue() {
//        Long id = 1L;
//        doNothing().when(productService).deleteProductById(id);
//        productService.deleteProductById(id);
//        verify(productService, times(1)).deleteProductById(id);
//    }
//
//    @Test
//    void deleteProduct_testDeleteProductWhenProductDoesNotExistThenFalse() {
//        Long id = 1L;
//        doThrow(new RuntimeException()).when(productService).deleteProductById(id);
//        assertThrows(RuntimeException.class, () -> productService.deleteProductById(id));
//        verify(productService, times(1)).deleteProductById(id);
//    }
//}