package com.example.avito.mapper;

import com.example.avito.dto.productdto.MyProductDto;
import com.example.avito.dto.productdto.ProductShowDto;
import com.example.avito.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public ResponseEntity<List<MyProductDto>> mapToMyProductDtos(List<Product> products) {
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
        return ResponseEntity.ok(myProductDtos);
    }

    public ProductShowDto mapToProductShowDto(Product product) {
        ProductShowDto productShowDto = new ProductShowDto();
        productShowDto.setCity(product.getCity());
        productShowDto.setPrice(product.getPrice());
        productShowDto.setDescription(product.getDescription());

        return productShowDto;
    }

}
