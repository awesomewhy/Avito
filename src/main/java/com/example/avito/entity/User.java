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
    private UUID id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String city;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @JsonBackReference
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private Collection<Review> reviews;

    @JsonBackReference
    @OneToMany(mappedBy = "creatorId", cascade = CascadeType.ALL)
    private Collection<Product> products;

}
