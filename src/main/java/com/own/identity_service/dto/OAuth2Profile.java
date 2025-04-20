package com.own.identity_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.own.identity_service.domain.enumeration.AuthProvider;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2Profile {
    @JsonProperty("sub")
    private String providerId;

    @JsonProperty("name")
    private String fullName;

    @JsonProperty("picture")
    private String avatar;

    @JsonProperty("email")
    private String email;

    private AuthProvider provider;
}
