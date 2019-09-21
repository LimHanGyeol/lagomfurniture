package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.OrderDetail;
import com.example.lagomfurniture.model.Product;
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
import java.util.List;

@Controller
@RequestMapping("/payment")
@Setter
public class PayController {

    @Autowired
    private KakaoPay kakaopay;

    KakaoPayApprovalVO kakaoPayApprovalVO;

    //상세보기에서 구매 페이지 이동 - 여기서 세션 없을경우 이동 못하게 해야함. 2019.08.12. Han Gyeol
    @PostMapping("/{id}")
    public String payment(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        model.addAttribute("product", kakaopay.getProductInfoById(id));
        return "view/shop/payment";
    }

    //KAKAOPAY
    @PostMapping("/kakaoPay")
    public String kakaoPay(String userName, String phone_num, String postcode,String roadAddress, String detailAddress,HttpServletRequest request, HttpSession session) {
        String productPrice = request.getParameter("productPrice");
        String productName = request.getParameter("productName");
        String productId = request.getParameter("productid");

        System.out.println("결제하기 입력 데이터 한결 : " + userName + " / " + phone_num  + " / " + roadAddress + " / " + detailAddress);

        User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        String sessionUser = user.getUserEmail();

        //ORDER DETAIL Object SESSION 저장
        OrderDetail orderDetail = new OrderDetail(userName,phone_num,postcode,roadAddress,detailAddress);
        session.setAttribute(HttpSessionUtils.ORDER_DETAIL,orderDetail);

        // 결제에 필요한 productId SESSION 저장
        session.setAttribute(HttpSessionUtils.PRODUCT_SESSION_ID,productId);

        System.out.println("price : " + productPrice + ", name : " + productName + "id :" + productId + "sessionId : " + sessionUser);
        System.out.println("::::::::::::::: KAKAOPAY POST::::::::::::::::");
        return "redirect:" + kakaopay.kakaoPayReady(productPrice,productName,productId,sessionUser);
    }


    //KAKAOPAY ON SUCCESS
    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) {
        System.out.println("::::::::::::::: KAKAOPAY SUCCESS GET::::::::::::::");
        System.out.println("KAKAOPAY SUCCESS pg_token" + pg_token);
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token,session));

        String productSessionId = (String) session.getAttribute(HttpSessionUtils.PRODUCT_SESSION_ID);
        System.out.println("SelectedProduct :  " + productSessionId);

        return "view/shop/kakaoPaySuccess";
    }

}
