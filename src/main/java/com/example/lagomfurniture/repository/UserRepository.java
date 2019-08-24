package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String user_email);
    List<User> findByid(Long user_id);
}