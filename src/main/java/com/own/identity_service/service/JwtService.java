package com.own.identity_service.service;

import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.util.annotation.ProviderType;
import com.own.identity_service.util.oauth2.OAuth2ApiClient;
import com.own.identity_service.util.oauth2.OAuth2ApiClientFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final Map<String, OAuth2ApiClientFactory> clientFactories;

    public JwtService(
            List<OAuth2ApiClientFactory> factories
    ) {
        this.clientFactories = factories.stream()
                .collect(Collectors.toMap(
                        f -> f.getClass().getAnnotation(ProviderType.class).value(),
                        f -> f
                ));
    }

    public OAuth2Profile verifyIdToken(String idToken, String provider) {
        OAuth2ApiClient client = clientFactories.get(provider.toLowerCase())
                .createClient();
        return client.getProfile(idToken);
    }

    public String generateToken(OAuth2Profile profile) {
        return "Laun";
    }
}
