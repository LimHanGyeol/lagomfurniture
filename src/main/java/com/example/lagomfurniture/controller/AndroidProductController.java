package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.service.android.AndroidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class AndroidProductController {

    @Autowired
    private AndroidService androidService;

    // Android Category Item findAll - get
    @GetMapping("/category_android")
    @ResponseBody
    public String bed_android(String category) {
        String productList = androidService.getAndroidCategory(category);
        return productList;
    }
}
