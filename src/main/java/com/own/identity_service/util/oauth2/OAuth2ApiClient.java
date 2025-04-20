package com.own.identity_service.util.oauth2;

import com.own.identity_service.dto.LineTokenResponse;
import com.own.identity_service.dto.OAuth2Profile;

public interface OAuth2ApiClient {
    LineTokenResponse exchangeCode(String code, String codeVerifier);
    OAuth2Profile getProfile(String accessToken);
}
