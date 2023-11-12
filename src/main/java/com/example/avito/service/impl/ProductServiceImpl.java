package com.example.avito.service.impl;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
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

import java.util.*;

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

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> addItem(@AuthenticationPrincipal String email, @RequestBody ProductDto productDto) {
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
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

        return getMyProductDtos(products);
    }

    private static List<MyProductDto> getMyProductDtos(List<Product> products) {
        List<MyProductDto> MyProductDto = new ArrayList<>();
        for (Product product : products) {
            MyProductDto myProductDto = new MyProductDto();
            myProductDto.setCity(product.getCity());
            myProductDto.setType(product.getType());
            myProductDto.setPrice(product.getPrice());
            myProductDto.setDateCreation(String.valueOf(product.getDateCreation()));
            myProductDto.setDescription(product.getDescription());

            MyProductDto.add(myProductDto);
        }
        return MyProductDto;
    }
}
