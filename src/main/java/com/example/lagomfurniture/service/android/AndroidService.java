package com.example.lagomfurniture.service.android;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.utils.UserPasswordHashClass;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AndroidService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserPasswordHashClass userPasswordHashClass;

    public Map<String, String> androidRegister(User user) {
        Map<String, String> androidRegister = new HashMap<>();
        System.out.println("Android Register 확인 - email : " + user.getUserEmail() + " password : " + user.getPassword());
        User RegisterUser = userRepository.findByUserEmail(user.getUserEmail());
        if (RegisterUser != null) {
            System.out.println("회원가입 실패 : 이메일 중복");
            androidRegister.put("response","exist");
            return androidRegister;
        }
        String hashedPassword = userPasswordHashClass.getSHA256(user.getPassword());
        user.setPassword(hashedPassword);

        System.out.println("회원가입 성공 : " + user);
        userRepository.save(user);
        System.out.println("Android Register 성공");
        androidRegister.put("response","success");
        System.out.println("Register User Json : " + androidRegister);

        return androidRegister;
    }

    public Map<String, String> androidLogin(String userEmail, String password) {
        Map<String, String> androidLogin = new HashMap<>();

        System.out.println("Android Login user 확인 : " + userEmail + " / " + password);
        User user = userRepository.findByUserEmail(userEmail);
        String hashedPassword = userPasswordHashClass.getSHA256(password);
        System.out.println("findByUserEmail 확인 : " + user);

        if (user == null) {                             // 입력값이 없을 경우 로그인 실패
            System.out.println("로그인 실패 : 입력값 및 유저 데이터 없음.");
            androidLogin.put("response","failure");
            return androidLogin;
        }

        if (!user.messagePasswordCheck(hashedPassword)) {     // 패스워드가 맞지 않을 경우 로그인 실패
            System.out.println("로그인 실패 : 비밀번호 틀림");
            androidLogin.put("response","failure");
            return androidLogin;
        }

        System.out.println("Android Login 성공");
        androidLogin.put("response","success");
        androidLogin.put("resultcode","1");
        androidLogin.put("nickname",user.getNickname());
        androidLogin.put("user_email",user.getUserEmail());
        androidLogin.put("profile_Image",user.getProfileImage());
        System.out.println("user json : " + androidLogin);

        return androidLogin;
    }

    public String getAndroidCategory(String category) {
        System.out.println("key : " + category);
        Gson gson = new Gson();
        List<Product> productList = productRepository.findByProductCategory(category);

        String categoryJson = gson.toJson(productList);
        System.out.println("product : " + productList);
        System.out.println("gson - productList : " + categoryJson);

        return categoryJson;
    }
}
