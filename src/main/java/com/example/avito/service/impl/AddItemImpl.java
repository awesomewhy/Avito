package com.example.avito.service.impl;

import com.example.avito.confings.JwtRequestFilter;
import com.example.avito.dtos.JwtRequest;
import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.service.AddItem;
import com.example.avito.utils.JwtTokenUtils;
import org.springframework.web.bind.annotation.RequestBody;

public class AddItemImpl implements AddItem {
    @Override
    public Product save(@RequestBody ProductDto productDto) {
        Long idCreator = new JwtTokenUtils().getUserIdFromToken();
        productDto.setId_creator(idCreator);
        return null;
    }
}
