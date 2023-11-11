package com.example.avito.service.impl;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.repository.ProductRepository;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
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
    UserService userService;

    @Override
    public Product addItem(@RequestBody ProductDto productDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            Product product = new Product();
            product.setIdCreator(user.get());
            product.setPrice(productDto.getPrice());
            product.setType(productDto.getType());
            product.setCity(user.get().getCity());
            product.setDateCreation(new Date());
            product.setDescription(productDto.getDescription());

            return productRepository.save(product);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Optional<User> user = userService.findByEmail(email);

        if(product.isPresent()) {
            if (Objects.equals(product.get().getIdCreator(), user.get().getId())) {
                productRepository.deleteById(id);
                return;
            }
            System.out.println("у вас нет такого предмета в продаже");
        }

    }

    @Override
    public List<MyProductDto> getMyProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();


        Optional<User> user = userService.findByEmail(email);
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
