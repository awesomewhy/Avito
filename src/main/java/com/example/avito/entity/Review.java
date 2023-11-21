package com.example.avito.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewerId;

    @Column(name = "type")
    private String type;

    @Column(name = "rating")
    private Byte rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "image")
    private String image;
}