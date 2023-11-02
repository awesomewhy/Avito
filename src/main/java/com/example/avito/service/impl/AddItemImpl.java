package com.example.avito.service.impl;

import com.example.avito.confings.JwtRequestFilter;
import com.example.avito.dtos.JwtRequest;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.repository.ProductRepository;
import com.example.avito.service.AddItem;
import com.example.avito.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AddItemImpl implements AddItem {

    private final ProductRepository productRepository;


    @Override
    public Product addItem(@RequestBody ProductDto productDto) {

        Product product = new Product();
        product.setIdCreator(2L);
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
//        product.setCity(idCreator.getCity);
        product.setCity("Moscow");
        product.setDateCreation(new Date());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }
}
