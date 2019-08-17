package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 카테고리 침대 : Product 의 메인 카테고리로 이동
    @GetMapping("/bed")
    public String bed(Model model) {
        String CATEGORY = "bed";
        List<Product> productList = productRepository.findByProductCategory(CATEGORY);
        model.addAttribute("product",productList);

        return "view/shop/product_category/product_bed";
    }

    // 카테고리 수납으로 이동
    @GetMapping("/chest")
    public String chest(Model model) {
        String CATEGORY = "chest";
        List<Product> productList = productRepository.findByProductCategory(CATEGORY);
        model.addAttribute("product",productList);
        return "view/shop/product_category/product_chest";
    }

    // 카테고리 책상
    @GetMapping("/table")
    public String table(Model model) {
        String CATEGORY = "table";
        List<Product> productList = productRepository.findByProductCategory(CATEGORY);
        model.addAttribute("product",productList);
        return "view/shop/product_category/product_table";
    }
    // 카테고리 의자
    @GetMapping("/chair")
    public String chair(Model model) {
        String CATEGORY = "chair";
        List<Product> productList = productRepository.findByProductCategory(CATEGORY);
        model.addAttribute("product",productList);
        return "view/shop/product_category/product_chair";
    }
    // 카테고리 조명
    @GetMapping("/lamp")
    public String lamp(Model model) {
        String CATEGORY = "lamp";
        List<Product> productList = productRepository.findByProductCategory(CATEGORY);
        model.addAttribute("product",productList);
        return "view/shop/product_category/product_lamp";
    }
}
