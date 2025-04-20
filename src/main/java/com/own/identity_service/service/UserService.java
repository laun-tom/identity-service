package com.own.identity_service.service;

import com.own.identity_service.domain.User;
import com.own.identity_service.domain.enumeration.AuthProvider;
import com.own.identity_service.dto.UserDto;
import com.own.identity_service.exception.AppException;
import com.own.identity_service.exception.ErrorCode;
import com.own.identity_service.mapper.UserMapper;
import com.own.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new AppException(ErrorCode.EXISTED_EMAIL);
        }
        User user = userMapper.toUser(userDto);
        if (user.getProvider() == AuthProvider.LOCAL) {
            user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User oAuth2Login(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            return this.userRepository.save(user);
        }
        if (existingUser.get().getProvider() != user.getProvider()) {
            throw new AppException(ErrorCode.EXISTED_EMAIL);
        }
        return existingUser.get();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
    }
}
