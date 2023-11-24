package com.example.avito.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UUID")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname = "";

    @Column(name = "password")
    private String password;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private Collection<Review> reviews;

    @OneToMany(mappedBy = "creatorId", cascade = CascadeType.ALL)
    private Collection<Product> products;

}
