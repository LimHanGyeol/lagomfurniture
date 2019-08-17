package com.example.lagomfurniture.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class ClientResources {

    // 이 부분은 application.properties 의 api 부분을 쉽게 가져오는 부분이고,
    // 클린코드화 할 수 있다고 생각한다. 이걸 써먹자 !

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }
    public ResourceServerProperties getResource() {
        return resource;
    }
}
