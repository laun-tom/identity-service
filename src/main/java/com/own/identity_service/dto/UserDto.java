package com.own.identity_service.dto;

import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.domain.enumeration.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @Email
    @NotNull(message = "Email is required")
    private String email;

    @Length(max = 11, message = "Phone number must be 11 digits")
    private String phone;

    @Length(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @Length(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotNull(message = "Password is required")
    private String password;

    private String avatar;

    private Gender gender;

    private AuthProvider provider;

    private String providerId;
}
