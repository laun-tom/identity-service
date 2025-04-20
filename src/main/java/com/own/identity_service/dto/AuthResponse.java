package com.own.identity_service.dto;

import com.own.identity_service.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private User user;
    private String token;

}
