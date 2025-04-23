package com.own.identity_service.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_followed")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFollowed extends BaseEntity{

    @Column(name = "user_id")
    private String userId;

}
