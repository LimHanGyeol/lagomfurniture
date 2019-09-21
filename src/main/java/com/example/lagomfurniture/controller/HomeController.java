package com.example.lagomfurniture.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("")
    public String home() {
        return "index";
    }

    // 마이 페이지로 이동
    @GetMapping("/mypage")
    public String myPage() {
        return "view/users/mypage";
    }
}
