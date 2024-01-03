package com.example.avito.service.impl.product;

import com.example.avito.dto.productdto.MyProductDto;
import com.example.avito.dto.otherdto.PriceSortDto;
import com.example.avito.dto.productdto.ProductSellDto;
import com.example.avito.dto.productdto.ProductShowDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.mapper.ProductMapper;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
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
    private final static String PRICE_HIGH = "цена слишком большая";
    private final static String PRODUCT_NOT_FOUND = "У вас нет такого предмета в продаже";
    private final static String START_PRICE_AND_END_PRICE_MUST_BE_PROVIDED = "Необходимо указать начальную и конечную цену";

    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductMapper productMapper;



    @Override
    public ResponseEntity<?> addItem(@RequestBody ProductSellDto productDto) {
        Optional<User> user = userService.getAuthenticationPrincipalUserByEmail();

        if (user.isPresent()) {
            Product product = new Product();
            product.setCreatorId(user.get());
            if (productDto.getPrice().compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0) {
                return ResponseEntity.badRequest().body(new ErrorResponse(400, PRICE_HIGH));
            }
            product.setPrice(productDto.getPrice());
            product.setType(productDto.getType());
            product.setCity(user.get().getCity());
            product.setDateCreation(new Date());
            product.setDescription(productDto.getDescription());
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(PRODUCT_ADDED_SUCCESSFULLY);
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, PRODUCT_NOT_ADDED));
        }
    }

    @Override
    @Cacheable("products")
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
    public List<ProductShowDto> sortByPrice(@RequestBody PriceSortDto priceSortDto) {
        if (priceSortDto.getStartPrice() == null || priceSortDto.getEndPrice() == null) {
            throw new IllegalArgumentException(START_PRICE_AND_END_PRICE_MUST_BE_PROVIDED);
        }
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice().compareTo(priceSortDto.getStartPrice()) >= 0 && p.getPrice().compareTo(priceSortDto.getEndPrice()) <= 0)
                .map(productMapper::mapToProductShowDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        Optional<User> user = userService.getAuthenticationPrincipalUserByEmail();

        if (product.isPresent() && user.isPresent()) {
            if (product.get().getCreatorId().getId().equals(user.get().getId())) {
                productRepository.deleteById(id);
                return ResponseEntity.ok().body(PRODUCT_DELETED_SUCCESSFULLY);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), PRODUCT_NOT_FOUND));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), USER_NOT_FOUND));
        }
    }

    @Override
//    @Caching(
//            cacheable = {
//                    @Cacheable("users"),
//                    @Cacheable("contacts")
//            },
//            put = {
//                    @CachePut("tables"),
//                    @CachePut("chairs"),
//                    @CachePut(value = "meals", key = "#product.email")
//            },
//            evict = {
//                    @CacheEvict(value = "services", key = "#product.name")
//            }
//    )
    public ResponseEntity<?> getMyProducts() {
        Optional<User> user = userService.getAuthenticationPrincipalUserByEmail();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
        }
        User currentUser = user.get();
        List<Product> products = productRepository.findAllByCreatorId(currentUser);

        return productMapper.mapToMyProductDtos(products);
    }
}