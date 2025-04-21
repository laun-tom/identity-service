package com.own.identity_service.service;

import com.own.identity_service.dto.OAuth2Profile;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String generateToken(OAuth2Profile profile) {
        return "Laun";
    }
}
