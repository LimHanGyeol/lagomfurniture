package com.example.lagomfurniture.service;

import com.example.lagomfurniture.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

//의존성 주입
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;

    public OrderInfoServiceImpl(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }
}
