package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String user_email);
}