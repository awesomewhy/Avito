package com.example.avito.service.impl;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.PriceSortDto;
import com.example.avito.dtos.ProductSellDto;
import com.example.avito.dtos.ProductShowDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.mapper.ProductMapper;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final static String USER_NOT_FOUND = "Пользователь не найден";
    private final static String PRODUCT_ADDED_SUCCESSFULLY = "Продукт успешно добавлен";
    private final static String PRODUCT_DELETED_SUCCESSFULLY = "Продукт успешно удален";
    private final static String PRODUCT_NOT_ADDED = "Продукт не добавлен";
    private final static String PRODUCT_NOT_FOUND = "У вас нет такого предмета в продаже";
    private final static String START_PRICE_AND_END_PRICE_MUST_BE_PROVIDED = "Необходимо указать начальную и конечную цену";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private ProductMapper productMapper;

    @Override
    public ResponseEntity<?> addItem(@AuthenticationPrincipal String email, @RequestBody ProductSellDto productDto) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            Product product = new Product();
            product.setIdCreator(user.get());
            product.setPrice(productDto.getPrice());
            product.setType(productDto.getType());
            product.setCity(user.get().getCity());
            product.setDateCreation(new Date());
            product.setDescription(productDto.getDescription());
            productRepository.save(product);
            return ResponseEntity.ok().body(PRODUCT_ADDED_SUCCESSFULLY);
        } else {
            throw new RuntimeException(PRODUCT_NOT_ADDED);
        }
    }

    @Override
    public List<ProductShowDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::mapToProductShowDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductShowDto> sortProductsByCity() {
        return productRepository.findAll().stream()
                .filter(p -> p.getCity().equals("London"))
                .map(productMapper::mapToProductShowDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductShowDto> sortByPrice(PriceSortDto priceSortDto) throws Exception {
        if (priceSortDto.getStartPrice() == null || priceSortDto.getEndPrice() == null) {
            throw new IllegalArgumentException(START_PRICE_AND_END_PRICE_MUST_BE_PROVIDED);
        }
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice().compareTo(priceSortDto.getStartPrice()) >= 0 && p.getPrice().compareTo(priceSortDto.getEndPrice()) <= 0)
                .map(productMapper::mapToProductShowDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> deleteProductById(@AuthenticationPrincipal String email, Long id) {
        Optional<Product> product = productRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(email);

        if (product.isPresent() && user.isPresent()) {
            if (product.get().getIdCreator().getId().equals(user.get().getId())) {
                productRepository.deleteById(id);
                return ResponseEntity.ok().body(PRODUCT_DELETED_SUCCESSFULLY);
            }
            return ResponseEntity.ok().body(PRODUCT_NOT_FOUND);
        } else {
            return ResponseEntity.ok().body(PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email) {
        Optional<User> user = userRepository.findByEmail(email);
        User currentUser = user.orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        List<Product> products = productRepository.findAllByIdCreator(currentUser);

        return productMapper.mapToMyProductDtos(products);
    }
}