package com.example.lagomfurniture.service;

import com.example.lagomfurniture.model.OrderInfo;
import com.example.lagomfurniture.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderInfoService {
    private OrderInfoRepository orderInfoRepository;

    public OrderInfoService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    @Transactional
    public OrderInfo orderInfoSaveAndUpdate(OrderInfo input){
        return orderInfoRepository.save(input);
    }
}
