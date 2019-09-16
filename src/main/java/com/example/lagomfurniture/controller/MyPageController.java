package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.service.PageMakerService;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyPageController {
    // 마이 페이지로 이동
    @GetMapping("/mypage")
    public String myPage() {
        return "view/users/mypage";
    }


}
