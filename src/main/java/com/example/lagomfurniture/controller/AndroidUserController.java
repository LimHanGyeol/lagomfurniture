package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class AndroidUserController {
    // DESC user; user_id , user_email , password , nickname , platform , profile_image
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login_android")
    @ResponseBody
    public Map<String, String> login_android(String userEmail, String password) {
        Map<String, String> loginResult = new HashMap<>();
        System.out.println("Android Login user 확인 : " + userEmail + " / " + password);
        User user = userRepository.findByUserEmail(userEmail);
        System.out.println("findByUserEmail 확인 : " + user);

        // 예외처리
        if (user == null) {                             // 입력값이 없을 경우 로그인 실패
            System.out.println("로그인 실패 : 입력값 및 유저 데이터 없음.");
            loginResult.put("response","failure");
            return loginResult;
        }

        if (!user.messagePasswordCheck(password)) {     // 패스워드가 맞지 않을 경우 로그인 실패
            System.out.println("로그인 실패 : 비밀번호 틀림");
            loginResult.put("response","failure");
            return loginResult;
        }

        System.out.println("Android Login 성공");
        loginResult.put("response","success");
        loginResult.put("nickname",user.getNickname());
        loginResult.put("user_email",user.getUserEmail());
        loginResult.put("profile_Image",user.getProfileImage());
        System.out.println("user json : " + loginResult);
        return loginResult;
    }


}
