package com.example.avito.entity;

import jakarta.persistence.*;
import lombok.*;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creatorId;

    private BigDecimal price;
    private String type;
    private String city;
    private Date dateCreation;
    private String description;

}
