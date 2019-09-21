package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductDetailController {


    @Autowired
    private ProductRepository productRepository;

    // 침대 카테고리의 상세보기
    @GetMapping("/bed/{id}")
    public String bed_detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/product_detail";
    }


    // 수납 카테고리의 상세보기
    @GetMapping("/chest/{id}")
    public String chest_detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/product_detail";
    }

    // 책상 카테고리의 상세보기
    @GetMapping("/table/{id}")
    public String table_detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/product_detail";
    }

    // 의자 카테고리의 상세보기
    @GetMapping("/chair/{id}")
    public String chair_detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/product_detail";
    }

    // 조명 카테고리의 상세보기
    @GetMapping("/lamp/{id}")
    public String lamp_detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/product_detail";
    }

















}
