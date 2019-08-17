package com.example.lagomfurniture.repository;

import com.example.lagomfurniture.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findByOrderDetailId(Long orderDetailId);
}
