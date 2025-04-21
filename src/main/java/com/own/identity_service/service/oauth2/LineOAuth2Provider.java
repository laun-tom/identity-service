package com.own.identity_service.service.oauth2;

import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.dto.OAuth2TokenResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.util.annotation.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ProviderType("line")
@Service
@RequiredArgsConstructor
public class LineOAuth2Provider implements OAuth2Provider {
    private final LineConfig config;
    private final RestTemplate restTemplate;

    private static final String RESPONSE_TYPE = "response_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CODE_CHALLENGE_METHOD = "code_challenge_method";
    private static final String SCOPE = "scope";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String STATE = "state";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CODE = "code";
    private static final String CODE_VERIFIER = "code_verifier";
    private static final String ID_TOKEN = "id_token";

    @Override
    public String generateAuthUrl(String state) {
        return UriComponentsBuilder.fromUriString(config.getAuthUrl())
                .queryParam(RESPONSE_TYPE, "code")
                .queryParam(CLIENT_ID, config.getClientId())
                .queryParam(REDIRECT_URI, config.getRedirectUri())
                .queryParam(STATE, state)
                .queryParam(SCOPE, "profile openid email")
                .queryParam(CODE_CHALLENGE_METHOD, "S256")
                .build()
                .toUriString();
    }

    @Override
    public OAuth2Profile handleCallBack(String code, String codeVerifier) {
        OAuth2TokenResponse tokenResponse = this.exchangeCode(code, codeVerifier);
        OAuth2Profile profile = this.getProfile(tokenResponse.getIdToken());
        profile.setProvider(AuthProvider.LINE);
        return profile;
    }

    @Override
    public OAuth2TokenResponse exchangeCode(String code, String codeVerifier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE, "authorization_code");
        body.add(CODE, code);
        body.add(REDIRECT_URI, config.getRedirectUri());
        body.add(CLIENT_ID, config.getClientId());
        body.add(CLIENT_SECRET, config.getClientSecret());
        body.add(CODE_VERIFIER, codeVerifier);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(
                config.getTokenUrl(),
                request,
                OAuth2TokenResponse.class
        );
    }

    @Override
    public OAuth2Profile getProfile(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(ID_TOKEN, token);
        body.add(CLIENT_ID, config.getClientId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(config.getVerifyUrl(), request, OAuth2Profile.class);
    }
}
