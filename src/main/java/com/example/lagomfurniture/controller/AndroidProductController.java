package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/product")
public class AndroidProductController {

    @Autowired
    private ProductRepository productRepository;

    // 안드로이드 카테고리 상품 리스트 불러오기
    @GetMapping("/category_android")
    @ResponseBody
    public String bed_android(String category) {
        System.out.println("key : " + category);
        Gson gson = new Gson();
        List<Product> productList = productRepository.findByProductCategory(category);

        String categoryJson = gson.toJson(productList);
        System.out.println("product : " + productList);
        System.out.println("gson - productList : " + categoryJson);
        return categoryJson;
    }
}
