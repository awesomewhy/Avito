package com.example.avito.mapper;

import com.example.avito.dto.MyProductDto;
import com.example.avito.dto.ProductShowDto;
import com.example.avito.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public List<MyProductDto> mapToMyProductDtos(List<Product> products) {
        List<MyProductDto> myProductDtos = new ArrayList<>();
        for (Product product : products) {
            MyProductDto myProductDto = new MyProductDto();
            myProductDto.setCity(product.getCity());
            myProductDto.setType(product.getType());
            myProductDto.setPrice(product.getPrice());
            myProductDto.setDateCreation(String.valueOf(product.getDateCreation()));
            myProductDto.setDescription(product.getDescription());

            myProductDtos.add(myProductDto);
        }
        return myProductDtos;
    }

    public ProductShowDto mapToProductShowDto(Product product) {
        ProductShowDto productShowDto = new ProductShowDto();
        productShowDto.setCity(product.getCity());
        productShowDto.setPrice(product.getPrice());
        productShowDto.setType(product.getType());
        productShowDto.setDescription(product.getDescription());

        return productShowDto;
    }

}
