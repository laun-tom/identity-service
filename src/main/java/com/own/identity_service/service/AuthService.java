package com.own.identity_service.service;

import com.own.identity_service.domain.User;
import com.own.identity_service.dto.AuthResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.mapper.UserMapper;
import com.own.identity_service.service.oauth2.OAuth2Provider;
import com.own.identity_service.util.annotation.ProviderType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final Map<String, OAuth2Provider> providers;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthService(
            List<OAuth2Provider> providerList,
            JwtService jwtService,
            UserService userService,
            UserMapper userMapper
    ) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(
                        p -> p.getClass().getAnnotation(ProviderType.class).value(),
                        p -> p
                ));
        this.jwtService = jwtService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public String generateAuthUrl(String state, String providerType) {
        OAuth2Provider provider = providers.get(providerType.toLowerCase());
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported provider: " + providerType);
        }
        return provider.generateAuthUrl(state);
    }

    public AuthResponse handleCallback(String providerType, String code, String codeVerifier) {
        OAuth2Provider provider = providers.get(providerType.toLowerCase());
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported provider: " + providerType);
        }

        OAuth2Profile profile = provider.handleCallBack(code, codeVerifier);
        User user = userService.oAuth2Login(userMapper.toUser(profile));

        return AuthResponse.builder()
                .user(user)
                .token(jwtService.generateToken(profile))
                .build();
    }
}
