package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductIdRepository extends JpaRepository<Product, Long> {
        Product findByProductId(Long productId);
        }
