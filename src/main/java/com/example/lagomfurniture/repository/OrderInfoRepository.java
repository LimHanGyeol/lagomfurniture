package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.OrderInfo;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    OrderInfo findByOrderId(Long orderId);
}
