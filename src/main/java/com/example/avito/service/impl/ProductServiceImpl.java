package com.example.avito.service.impl;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.repository.ProductRepository;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    UserRepository userRepository;

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
            return ResponseEntity.ok().body("продукт успешно добавлен");
        } else {
            throw new RuntimeException("Продукт не добавлен");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(@AuthenticationPrincipal String email, Long id) {
        Optional<Product> product = productRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(email);

        if (product.isPresent() && user.isPresent()) {
            if (product.get().getIdCreator().getId().equals(user.get().getId())) {
                productRepository.deleteById(id);
                return;
            }
            System.out.println("У вас нет такого предмета в продаже");
        }

    }

    @Override
    public List<MyProductDto> getMyProducts(@AuthenticationPrincipal String email) {
        Optional<User> user = userRepository.findByEmail(email);
        User currentUser = user.orElseThrow(() -> new RuntimeException("Пользователь не найден"));
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
