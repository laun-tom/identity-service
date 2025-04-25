package com.own.identity_service.controller;

import com.own.identity_service.domain.User;
import com.own.identity_service.dto.AuthResponse;
import com.own.identity_service.dto.UserDto;
import com.own.identity_service.service.AuthService;
import com.own.identity_service.service.UserService;
import com.own.identity_service.service.richmenu.RichMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final RichMenuService richMenuService;

    @GetMapping("/login")
    public ResponseEntity<String> getAuthUrl(@RequestParam("loginType") String loginType) {
        String state = UUID.randomUUID().toString();
        return ResponseEntity.ok(authService.generateAuthUrl(state, loginType));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(this.userService.createUser(userDto));
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthResponse> callback(
            @RequestParam String code,
            @RequestParam("providerType") String providerType,
            @RequestParam String codeVerifier) {
        return ResponseEntity.ok(authService.handleCallback(providerType, code, codeVerifier));
    }

    @GetMapping("/rich-menu")
    public void richMenu() throws Exception {
        richMenuService.createAndLinkRichMenu();
    }
}
