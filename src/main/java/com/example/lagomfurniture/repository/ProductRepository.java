package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //List<Product> findByProductCategory(String productCategory);
    List<Product> findByProductId(Long productId);

    Page<Product> findByProductCategory(String productCategory, Pageable pageable);

}
