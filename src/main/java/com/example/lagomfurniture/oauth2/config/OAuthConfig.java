package com.example.lagomfurniture.oauth2.config;

import com.example.lagomfurniture.oauth2.social.GoogleOAuth2ClientAuthenticationProcessingFilter;
import com.example.lagomfurniture.service.SnsDataCertificationService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
public class OAuthConfig {

    private final OAuth2ClientContext oauth2ClientContext;
    private final SnsDataCertificationService snsDataCertificationService;

    public OAuthConfig(OAuth2ClientContext oauth2clientContext, SnsDataCertificationService snsDataCertificationService) {
        this.oauth2ClientContext = oauth2clientContext;
        this.snsDataCertificationService = snsDataCertificationService;
    }


    @Bean
    public Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new GoogleOAuth2ClientAuthenticationProcessingFilter(snsDataCertificationService); // 인자에 아무것도 안넣어야 데이터 반환됨
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(googleClient(), oauth2ClientContext);
        filter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), googleClient().getClientId());
        //filter.setTokenServices(new UserInfoTokenServices(googleResource().getUserInfoUri(), googleClient().getClientId()));
        filter.setTokenServices(tokenServices);
        return filter;
    }


        @Bean
        @ConfigurationProperties("security.oauth2.client")
        public OAuth2ProtectedResourceDetails googleClient() {
            return new AuthorizationCodeResourceDetails();
        }

        @Bean
        @ConfigurationProperties("security.oauth2.resource")
        public ResourceServerProperties googleResource() {
            return new ResourceServerProperties();
        }


/*
    @Bean
    @ConfigurationProperties("security.oauth2.resource")
    public ClientResources google() {
        return new ClientResources();
    }
*/
/*
    public Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(google(), "/snslogin/google")); //  이전에 등록했던 OAuth 리다이렉트 URL
        //filters.add(ssoFilter(facebook(), "/login/facebook"));
        filter.setFilters(filters);
        return filter;
    }
*/
/*
    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(restTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);
        return filter;
    }
*/

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
