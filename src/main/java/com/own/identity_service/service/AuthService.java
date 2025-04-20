package com.own.identity_service.service;

import com.own.identity_service.config.oauth2.GoogleConfig;
import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.domain.User;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.dto.AuthResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.dto.LineTokenResponse;
import com.own.identity_service.mapper.UserMapper;
import com.own.identity_service.util.LineApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final LineApiClient lineApiClient;
    private final JwtService jwtService;
    private final LineConfig lineConfig;
    private final GoogleConfig googleConfig;
    private final UserService userService;
    private final UserMapper userMapper;

    public String generateAuthUrl(String state, String loginType) {
        UriComponentsBuilder authUrl = switch (loginType.toLowerCase()) {
            case "line" -> UriComponentsBuilder.fromUriString(lineConfig.getAuthUrl())
                    .queryParam("client_id", lineConfig.getClientId())
                    .queryParam("redirect_uri", lineConfig.getRedirectUri());
            case "google" -> UriComponentsBuilder.fromUriString(googleConfig.getAuthUrl())
                    .queryParam("client_id", googleConfig.getClientId())
                    .queryParam("redirect_uri", googleConfig.getRedirectUri());
            default -> throw new IllegalArgumentException("Unsupported login type: " + loginType);
        };
        return authUrl
                .queryParam("response_type", "code")
                .queryParam("state", state)
                .queryParam("scope", "profile%20openid%20email")
                .queryParam("code_challenge_method", "S256")
                .build()
                .toUriString();
    }

    public AuthResponse handleCallback(String code, String codeVerifier) {
        LineTokenResponse tokenResponse = lineApiClient.exchangeCode(code, codeVerifier);
        OAuth2Profile profile = jwtService.verifyIdToken(tokenResponse.getIdToken());
        profile.setProvider(AuthProvider.LINE);
        User user = this.userService.oAuth2Login(this.userMapper.toUser(profile));

        return AuthResponse.builder()
                .user(user)
                .token(jwtService.generateToken(profile))
                .build();
    }
}
