package com.example.lagomfurniture.service;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class SnsDataCertificationService {
    private HttpSession httpSession;
    private SnsUserService snsUserService;

    // 먼저 isExistUser 을 해서 user에 데이터들이 있는지 확인한다.
    // 데이터가 있으면 유저를 리턴한다(로그인성공)
    // 그게 아니면 회원 가입한다.

    public UsernamePasswordAuthenticationToken doAuthentication(User snsLoginUser){
        if(snsUserService.isExistUser(snsLoginUser)){
            //기존 회원일 경우에 데이터베이스 에서 조회해서 인증 처리
            final User user = snsUserService.snsUserLogin(snsLoginUser);
            System.out.println("기존 회원일 경우 조회 후 인증 user : "+user);
            httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY,user);
            System.out.println("기존 회원 세션 처리 완료");
            return setAuthenticationToken(user);
        } else {
            //새 회원일 경우 회원가입 이후 인증 처리
            final User user = snsUserService.snsUserRegister(snsLoginUser);
            System.out.println("새 회원일 경우 회원가입 user : "+user);
            httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY,user);
            System.out.println("신규 회원 세션 처리 완료");
            return setAuthenticationToken(user);
        }
    }

    private UsernamePasswordAuthenticationToken setAuthenticationToken(Object user) {
        return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
