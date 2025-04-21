package com.own.identity_service.service.oauth2;

import com.own.identity_service.config.oauth2.GoogleConfig;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.dto.OAuth2TokenResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.util.annotation.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ProviderType("google")
@Service
@RequiredArgsConstructor
public class GoogleOAuth2Provider implements OAuth2Provider {
    private final GoogleConfig config;
    private final RestTemplate restTemplate;

    @Override
    public String generateAuthUrl(String state) {
        return UriComponentsBuilder.fromUriString(config.getAuthUrl())
                .queryParam(Constant.RESPONSE_TYPE, "code")
                .queryParam(Constant.CLIENT_ID, config.getClientId())
                .queryParam(Constant.REDIRECT_URI, config.getRedirectUri())
                .queryParam(Constant.STATE, state)
                .queryParam(Constant.SCOPE, "openid%20profile%20email")
                .queryParam(Constant.CODE_CHALLENGE_METHOD, "S256")
                .build()
                .toUriString();
    }

    @Override
    public OAuth2Profile handleCallBack(String code, String codeVerifier) {
        OAuth2TokenResponse tokenResponse = this.exchangeCode(code, codeVerifier);
        OAuth2Profile profile = this.getProfile(tokenResponse.getAccessToken());
        profile.setProvider(AuthProvider.GOOGLE);
        return profile;
    }

    @Override
    public OAuth2TokenResponse exchangeCode(String code, String codeVerifier) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(Constant.GRANT_TYPE, "authorization_code");
        body.add(Constant.CODE, code);
        body.add(Constant.REDIRECT_URI, config.getRedirectUri());
        body.add(Constant.CLIENT_ID, config.getClientId());
        body.add(Constant.CLIENT_SECRET, config.getClientSecret());
        body.add(Constant.CODE_VERIFIER, codeVerifier);

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
        headers.setBearerAuth(token);

        return restTemplate.exchange(
                config.getVerifyUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                OAuth2Profile.class
        ).getBody();
    }
}
