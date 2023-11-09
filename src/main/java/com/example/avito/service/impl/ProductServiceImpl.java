package com.example.avito.service.impl;

import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.repository.ProductRepository;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public Product addItem(@RequestBody ProductDto productDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Optional<User> user = userService.findByEmail(email);

        Product product = new Product();
        product.setIdCreator(user.get().getId());
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
        product.setCity(user.get().getCity());
        product.setDateCreation(new Date());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
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
    public List<Product> getMyProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Optional<User> user = userService.findByEmail(email);

        return productRepository.findAll().stream()
                .filter(product -> Objects.equals(product.getIdCreator(), user.get().getId()))
                .collect(Collectors.toList());
    }
}
