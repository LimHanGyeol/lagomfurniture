package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Cart;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.CartRepository;
import com.example.lagomfurniture.repository.ProductIdRepository;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductIdRepository productIdRepository;
    @Autowired
    private CartRepository cartRepository;

    //상품 상세보기에서 해당 상품 장바구니에 담기
    @PostMapping("/{id}")
    public String cartData(@PathVariable Long id, Model model, HttpServletRequest request, HttpSession session) {

        model.addAttribute("product", productRepository.findById(id).get());

        System.out.println( "상품 id : " + id);
        Product addedproduct = productIdRepository.findByProductId(id);
        System.out.println(addedproduct);

        User loginUser = HttpSessionUtils.getUserSession(session);

        Cart cart = new Cart(loginUser,addedproduct,1);
        cartRepository.save(cart);
        System.out.println(cartRepository.findAll());

        return "";
    }

    //장바구니 페이지 (담은 상품 전체보기)
    @GetMapping("/all")
    public String cart(Model model, HttpSession session){

        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }

        User loginUser = HttpSessionUtils.getUserSession(session);
        List<Cart> carts = cartRepository.findByUser(loginUser);
        System.out.println(carts);

        model.addAttribute("cart",cartRepository.findByUser(loginUser));

        return "view/shop/cart";
    }

}
