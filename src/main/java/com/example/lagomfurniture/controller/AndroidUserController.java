package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.service.android.AndroidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class AndroidUserController {
    // DESC user; user_id , user_email , password , nickname , platform , profile_image
    @Autowired
    private AndroidService androidService;

    // Android Login : Post
    @PostMapping("/android_login")
    @ResponseBody
    public Map<String, String> login_android(String userEmail, String password) {
        return androidService.androidLogin(userEmail, password);
    }

    // Android register : Post
    @PostMapping("/android_register")
    @ResponseBody
    public Map<String, String> register_android(User user) {
        return androidService.androidRegister(user);
    }

}