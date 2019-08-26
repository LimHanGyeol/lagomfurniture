package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 페이지 이동
    @GetMapping("")
    public String home(Model model) {
        List<Review> reviewList = reviewRepository.findAll();
        System.out.println("review list : " + reviewList);
        model.addAttribute("review",reviewList);
        return "view/review/review";
    }
}
