package com.example.lagomfurniture.service;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SnsUserService {
    // 먼저 isExistUser 을 해서 user에 데이터들이 있는지 확인한다.
    // 데이터가 있으면 유저를 리턴한다(로그인성공)
    // 그게 아니면 회원 가입한다.

    private UserRepository userRepository;

    //회원가입
    public User snsUserRegister(User snsLoginUser){
        System.out.println("SNS LOGIN REGISTER USER : " + snsLoginUser);
        return userRepository.save(snsLoginUser);
    }

    //데이터가 있으면 로그인
    public User snsUserLogin(User snsLoginUser){
        final User user = userRepository.findByUserEmail(snsLoginUser.getUserEmail());
        if(user == null){
            System.out.println("로그인 실패 : 입력값 없음");
            throw new RuntimeException();
        }
        if(!user.messagePasswordCheck(snsLoginUser.getPassword())){
            System.out.println("로그인 실패 : 비밀번호 틀림");
            throw new RuntimeException();
        }
        return user;
    }
    // 이메일 체크
    public boolean isExistUser(User snsLoginUser){
        final User user = userRepository.findByUserEmail(snsLoginUser.getUserEmail());
        System.out.println("SNS LOGIN 한 유저가 기존 유저인지 확인 : user " + user);
        return (user != null);
    }

//    // 닉네임 체크
//    public boolean isExistUserNickname(User RegisterUser) {
//        User user = userRepository.findByNickname(RegisterUser.getNickname());
//        System.out.println("입력한 닉네임을 가지고 있는 유저가 있는지 확인 : " + user);
//        return (user != null);
//    }


}
