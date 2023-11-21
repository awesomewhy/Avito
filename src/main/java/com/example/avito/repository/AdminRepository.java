package com.example.avito.repository;

import com.example.avito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<User, UUID> {
}
