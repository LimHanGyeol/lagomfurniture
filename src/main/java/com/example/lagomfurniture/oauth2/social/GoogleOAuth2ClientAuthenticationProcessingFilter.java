package com.example.lagomfurniture.oauth2.social;

import com.example.lagomfurniture.model.GoogleApiLoginData;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.service.SnsDataCertificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GoogleOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

    private ObjectMapper mapper = new ObjectMapper();
    private SnsDataCertificationService snsDataCertificationService;



    public GoogleOAuth2ClientAuthenticationProcessingFilter(SnsDataCertificationService snsDataCertificationService) {
       super("/snslogin/google");
       this.snsDataCertificationService = snsDataCertificationService;
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final OAuth2AccessToken accessToken = restTemplate.getAccessToken(); // 토큰 정보 가져옴
        System.out.println("Google Api 에서 내려온 accessToken : " + accessToken);
        final OAuth2Authentication auth = (OAuth2Authentication) authResult;
        final Object details = auth.getUserAuthentication().getDetails(); // 소셜에서 넘겨 받은 정보를 details에 저장
        System.out.println("Google Api 에서 내려온 google user : " + details);
        final GoogleApiLoginData googleApiLoginData = mapper.convertValue(details, GoogleApiLoginData.class);
        googleApiLoginData.setAccessToken(accessToken); // access token 정보도 저장
        //final UserConnection userConnection = UserConnection.valueOf(googleApiLoginData); // UserConnection를 googleApiLoginData 기반으로 생성
        User user = new User(googleApiLoginData.getUser_email(), "google",googleApiLoginData.getNickname(),googleApiLoginData.getPassword(),googleApiLoginData.getProfile_image());
        System.out.println("google login user 를 User 객체에 매핑 : " + user) ;
        final UsernamePasswordAuthenticationToken authenticationToken = snsDataCertificationService.doAuthentication(user); // SocialService를 이용해서 인증 절차 진행
        System.out.println("인증 절차 진행 완료");
        super.successfulAuthentication(request, response, chain, authenticationToken);

    }
}
