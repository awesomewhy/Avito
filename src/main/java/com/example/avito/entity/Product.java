package com.example.avito.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "id_creator")
    private Long id_creator;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "date_creation")
    private Date date_creation;

    @Column(name = "description")
    private Date description;

}
