package com.own.identity_service.util.oauth2;

import com.own.identity_service.config.oauth2.LineConfig;
import com.own.identity_service.util.annotation.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@ProviderType(value = "line")
@Component
@RequiredArgsConstructor
public class LineApiClientFactory implements OAuth2ApiClientFactory {
    private final RestTemplate restTemplate;
    private final LineConfig config;

    @Override
    public OAuth2ApiClient createClient() {
        return new LineApiClient(restTemplate, config);
    }
}
