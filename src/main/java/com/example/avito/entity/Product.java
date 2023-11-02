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
    @Column(name = "idCreator")
    private Long idCreator;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "dateCreation")
    private Date dateCreation;

    @Column(name = "description")
    private String description;

}
