package com.example.lagomfurniture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleApiLoginData {


    @JsonProperty("sub")
    private String password; //sub

    @JsonProperty("name")
    private String nickname; //name

    //private String given_name;
    @JsonProperty("picture")
    private String profile_image; //picture

    @JsonProperty("email")
    private String user_email; // email

    //private String profile;

    //private String family_name;
    //private boolean email_verified;
    //private String gender;
    //private String locale;
    private long expiration;
    private String access_token;

//google user : {sub=112868557270405161258, name=HanGyeol, given_name=HanGyeol,
// picture=https://lh4.googleusercontent.com/-a7XTgRVaytA/AAAAAAAAAAI/AAAAAAAABPA/oBPe17ZVpYM/photo.jpg,
// email=hang19663@gmail.com, email_verified=true, locale=ko}

    // user DB : id , user_email , platform , nickname , password , profile_image
    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.access_token = accessToken.getValue();
        this.expiration = accessToken.getExpiration().getTime();
    }

}
