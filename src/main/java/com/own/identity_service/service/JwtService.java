package com.own.identity_service.service;

import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.util.LineApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final LineApiClient lineApiClient;

    public OAuth2Profile verifyIdToken(String idToken) {
        return lineApiClient.getProfile(idToken);
    }

    public String generateToken(OAuth2Profile profile) {
        return "Laun";
    }
}
