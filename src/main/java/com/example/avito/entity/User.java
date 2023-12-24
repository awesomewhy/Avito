package com.example.avito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(unique = true, name = "city")
    private String city;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "city_id")
//    private City city;

    @JsonBackReference
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private Collection<Review> reviews;

    @JsonBackReference
    @OneToMany(mappedBy = "creatorId", cascade = CascadeType.ALL)
    private Collection<Product> products;

}
