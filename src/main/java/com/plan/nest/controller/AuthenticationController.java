package com.plan.nest.controller;

import com.plan.nest.repository.ServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final ServiceUserRepository serviceUserRepository;

    @GetMapping("/login")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal OAuth2User oAuth2User){
        if(oAuth2User == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 에러");
        }
        Map<String, Object> oAuth2UserInfo = oAuth2User.getAttributes();
        return ResponseEntity.ok(oAuth2UserInfo);
    }
}
