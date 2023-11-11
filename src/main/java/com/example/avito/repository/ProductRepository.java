package com.example.avito.repository;

import com.example.avito.dtos.MyProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIdCreator(User idCreator);

}
