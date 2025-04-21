package com.own.identity_service.service.oauth2;

import com.own.identity_service.dto.OAuth2TokenResponse;
import com.own.identity_service.dto.OAuth2Profile;

public interface OAuth2Provider {
    String generateAuthUrl(String state);

    OAuth2Profile handleCallBack(String code, String codeVerifier);

    OAuth2TokenResponse exchangeCode(String code, String codeVerifier);

    OAuth2Profile getProfile(String token);
}
