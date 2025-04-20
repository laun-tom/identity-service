package com.own.identity_service.config.oauth2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseConfig {
    protected String clientId;
    protected String clientSecret;
    protected String redirectUri;
    protected String authUrl;
    protected String tokenUrl;
    protected String verifyUrl;
}
