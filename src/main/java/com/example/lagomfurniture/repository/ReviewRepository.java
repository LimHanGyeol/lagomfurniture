package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
