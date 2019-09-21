package com.example.lagomfurniture.service.cartservice;

import com.example.lagomfurniture.model.Cart;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.CartRepository;
import com.example.lagomfurniture.repository.ProductIdRepository;
import com.example.lagomfurniture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductIdRepository productIdRepository;

    //장바구니 담은 상품 정보 id로 찾기
    public Product getProductInfoById(Long productId){
        return productIdRepository.findByProductId(productId);
    }

    //장바구니 상품 DB 저장
    public Cart saveCartList(Cart cart){
        return cartRepository.save(cart);
    }

    //장바구니 전체보기
    public List<Cart> getUserCartList(User user){
        return cartRepository.findByUser(user);
    }
}
