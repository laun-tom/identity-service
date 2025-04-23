package com.own.identity_service.repository;

import com.own.identity_service.domain.UserFollowed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowedResponse extends JpaRepository<UserFollowed, Long> {
}
