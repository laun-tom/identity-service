package com.own.identity_service.util.oauth2;

import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.dto.LineTokenResponse;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.util.annotation.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ProviderType("line")
@Component
@RequiredArgsConstructor
public class LineApiClient implements OAuth2ApiClient {
    private final RestTemplate restTemplate;
    private final LineConfig config;

    @Override
    public LineTokenResponse exchangeCode(String code, String codeVerifier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", config.getRedirectUri());
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("code_verifier", codeVerifier);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(
                config.getTokenUrl(),
                request,
                LineTokenResponse.class
        );
    }

    @Override
    public OAuth2Profile getProfile(String idToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id_token", idToken);
        body.add("client_id", config.getClientId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(config.getVerifyUrl(), request, OAuth2Profile.class);
    }
}
