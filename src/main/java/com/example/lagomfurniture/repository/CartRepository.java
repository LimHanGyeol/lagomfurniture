package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.Cart;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
