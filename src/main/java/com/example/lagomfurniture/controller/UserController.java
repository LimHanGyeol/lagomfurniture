package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.service.KakaoAPI;
import com.example.lagomfurniture.service.userservice.UserService;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import com.example.lagomfurniture.utils.UserPasswordHashClass;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private KakaoAPI kakao;

    @Autowired
    private UserService userService;
    @Autowired
    private UserPasswordHashClass userPasswordHashClass;

    //로그인 화면으로 이동
    @GetMapping("/loginForm")
    public String loginFormPage() {
        return "view/users/login";
    }

    //회원 가입 화면으로 이동
    @GetMapping("/signup")
    public String registerFormPage() {
        return "view/users/register";
    }

    //회원가입 데이터 전달
    @PostMapping("/register") //form태그 action 에서 이동됨
    public String register(User user) {
        System.out.println("user: " + user);
        userService.registerSave(user);
        return "redirect:/users/loginForm"; // 회원가입 끝나면 로그인화면으로 이동
    }

    //로그인 데이터 전달
    @PostMapping("/login")
    public String login(String userEmail, String password, HttpSession session) {
        User user = userService.loginRead(userEmail);
        String hashedPassword = userPasswordHashClass.getSHA256(password);

        if (user == null) {
            System.out.println("등록된 아이디 없음");
            return "redirect:/users/loginForm";
        }
        if (!user.messagePasswordCheck(hashedPassword)) {
            System.out.println("비밀번호 틀림");
            return "redirect:/users/loginForm";
        }
        //로그인 성공, 세션에 로그인 정보 저장
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        System.out.println("세션에 저장되는 User정보 :  " + user);
        System.out.println("sessionid: " + session);
        return "redirect:/";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        System.out.println("세션해제 로그아웃");
        return "redirect:/";
    }


    //카카오 로그인 Api
    @RequestMapping(value = "/social/kakaologin")
    public String kakaologin(@RequestParam("code") String code, HttpSession session){
        System.out.println("code : " + code);
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token,session);
        System.out.println("login Controller : " + userInfo);

        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        return "redirect:/";

    }

    //카카오 로그아웃
    @RequestMapping(value = "/logout")
    public String kakaologout(HttpSession session) {
        kakao.kakaoLogout((String) session.getAttribute("access_Token"));
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        session.removeAttribute("userId");
        return "redirect:/";
    }

}
