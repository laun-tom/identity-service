package com.own.identity_service.service.oauth2;

import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.dto.LineTokenResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.service.JwtService;
import com.own.identity_service.util.annotation.ProviderType;
import com.own.identity_service.util.oauth2.LineApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@ProviderType("line")
@Service
@RequiredArgsConstructor
public class LineOAuth2Provider implements OAuth2Provider {
    private final LineConfig config;
    private final LineApiClient apiClient;
    private final JwtService jwtService;

    @Override
    public String generateAuthUrl(String state) {
        return UriComponentsBuilder.fromUriString(config.getAuthUrl())
                .queryParam("response_type", "code")
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("state", state)
                .queryParam("scope", "profile openid email")
                .queryParam("code_challenge_method", "S256")
                .build()
                .toUriString();
    }

    @Override
    public OAuth2Profile handleCallBack(String code, String codeVerifier) {
        LineTokenResponse tokenResponse = apiClient.exchangeCode(code, codeVerifier);
        OAuth2Profile profile = jwtService.verifyIdToken("line", tokenResponse.getIdToken());
        profile.setProvider(AuthProvider.LINE);
        return profile;
    }
}
