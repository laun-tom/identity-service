package com.own.identity_service.util;

import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.dto.LineTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class LineApiClient {
    private final RestTemplate restTemplate;
    private final LineConfig lineConfig;

    public LineTokenResponse exchangeCode(String code, String codeVerifier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", lineConfig.getRedirectUri());
        body.add("client_id", lineConfig.getClientId());
        body.add("client_secret", lineConfig.getClientSecret());
        body.add("code_verifier", codeVerifier);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(lineConfig.getTokenUrl(), request, LineTokenResponse.class);
    }

    public OAuth2Profile getProfile(String idToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id_token", idToken);
        body.add("client_id", lineConfig.getClientId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(lineConfig.getVerifyUrl(), request, OAuth2Profile.class);
    }
}
