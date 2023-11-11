package com.example.avito.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCreator")
    private User idCreator;

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
