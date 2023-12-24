package com.example.avito.repository;

import com.example.avito.entity.User;
import org.hibernate.sql.exec.spi.JdbcCallParameterRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<User, UUID> {
}
