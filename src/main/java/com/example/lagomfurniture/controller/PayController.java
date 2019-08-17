package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.OrderInfoRepository;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.service.kakaopay.KakaoPay;
import com.example.lagomfurniture.service.kakaopay.KakaoPayApprovalVO;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payment")
@Setter
public class PayController {

    @Autowired
    private KakaoPay kakaopay;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserRepository userRepository;

    KakaoPayApprovalVO kakaoPayApprovalVO;

    //상세보기에서 구매 페이지 이동
    @PostMapping("/{id}")
    public String payment(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/payment";
    }

    //KAKAOPAY
    @PostMapping("/kakaoPay")
    public String kakaoPay(HttpServletRequest request, HttpSession session) {
        String productPrice = request.getParameter("productPrice");
        String productName = request.getParameter("productName");
        String productid = request.getParameter("productid");

        User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        String sessionUser = user.getUserEmail();


        System.out.println("price : " + productPrice + ", name : " + productName + "id :" + productid + "sessionId : " + sessionUser);
        System.out.println("::::::::::::::: KAKAOPAY POST::::::::::::::::");
        return "redirect:" + kakaopay.kakaoPayReady(productPrice,productName,productid,sessionUser);
    }


    //KAKAOPAY ON SUCCESS
    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) {
        System.out.println("::::::::::::::: KAKAOPAY SUCCESS GET::::::::::::::");
        System.out.println("KAKAOPAY SUCCESS pg_token" + pg_token);

//        System.out.println(partner_order_id+ ":     확인 partner_order_id");

        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token,session));
//        model.addAttribute("product", productRepository.findById(id).get());
        return "view/shop/kakaoPaySuccess";
    }

}
