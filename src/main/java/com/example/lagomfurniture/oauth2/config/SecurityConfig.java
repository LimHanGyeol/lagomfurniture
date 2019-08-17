package com.example.lagomfurniture.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;


/**
 * 스프링 시큐리티 모듈을 사용할 때, 애플리케이션의 종류와 관계없이 먼저 할 일이 스프링 시큐리티 모듈 추가이다.
 * Configuration : XML 과 자바를 통한 스프링 시큐리티 설정을 지원한다.
 * SecurityConfig : 스프링 웹 보안을 활성화 하는 가장 간단한 클래스 설정
 */
//@Log

/* @EnableWebSecurity 는 웹 보안을 활성화 한다는 뜻. 그 자체로는 사용되지 않고, 스프링 시큐리티가
 * WebSecurityConfigure 를 구현하고나 컨텍스트의 WebSecurityConfigureAdapter를
 * 확장한 빈으로 설정되어 있어야 한다.
 * WebSecurityConfigureAdapter 를 확장하여 클래스를 설정하는 것이 가장 보편적이다.
 */
//@EnableOAuth2Sso

@Configuration
@EnableWebSecurity
// SecurityConfig 를 만들면 모든 접근에 대해 인가(인증된 사용자가 요청된 자원에 접근 가능한지 결정하는 절차) 가 필요하게 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Filter ssoFilter;


    public SecurityConfig(Filter ssoFilter) {
        this.ssoFilter = ssoFilter;
    }

    // 다른 URL path 들에 대해 선택적으로 보안을 적용하기 위한 configure(HttpSecurity)의 오버라이딩
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/resources/**", "/", "/**", "/google**", "/login**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()  // 오픈카톡 예제로 적용한것
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")) // 오픈카톡 예제로 적용한것
                // 조졸두 씨 예제로 적용한 것
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().disable()
                .addFilterBefore(ssoFilter, BasicAuthenticationFilter.class); // OAuthConfig에서 생성한 ssoFilter 추가;
                //.addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
    }

}
