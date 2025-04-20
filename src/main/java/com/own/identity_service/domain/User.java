package com.own.identity_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.domain.enumeration.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
        indexes = @Index(name = "idx_user_email", columnList = "email")
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;
}
