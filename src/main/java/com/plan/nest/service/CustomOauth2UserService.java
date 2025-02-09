package com.plan.nest.service;

import com.plan.nest.dto.NaverResponse;
import com.plan.nest.dto.OAuth2Response;
import com.plan.nest.repository.RoleRepository;
import com.plan.nest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService는 OAuth2UserService의 구현체라서
    // DefaultOAuth2UserService 또는 OAuth2UserService 아무거나 상속해도 된다.

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // loadUser --> 네이버 사용자 인증 정보를 받아오는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());

        // 네이버, 구글, 깃허브 등등 어떤 어떤 인증 값인지 구별하기 위해 인증 제공자를 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 각 사이트마다 인증 데이터 규격이 다르기 때문에 OAuth2Response 를 만들어 구별
        // 인증 제공자에 따라 OAuth2Response를 생성
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            log.info("naver 로그인");
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else {
            System.out.println("로그인 실패");
            throw new IllegalArgumentException("지원하지 않는 로그인 제공자입니다.");
        }

        String provider = oAuth2Response.getProvider();
        String providerId = oAuth2Response.getProviderId();

        String username = provider + " " + providerId;
//        Optional<User> userOptional = userRepository.findByUsername(username);
//
//        String roleName = "USER";
//        Optional<Role> roleOptional = roleRepository.findByName(roleName);
//        Role role;
//        if (roleOptional.isEmpty()) {
//            role = new Role(roleName);
//            role = roleRepository.save(role);  // Save the new role and get the persisted instance
//        } else {
//            role = roleOptional.get();
//        }
//
//        User user;
//        if (userOptional.isEmpty()) {
//            user = User.builder()
//                    .username(username)
//                    .email(oAuth2Response.getEmail())
//                    .roles(Set.of(role))
//                    .loginId(oAuth2Response.getProviderId())
//                    .provider(oAuth2Response.getProvider())
//                    .password("defaultPassword")
//                    .registrationDate(LocalDateTime.now())
//                    .usernick(oAuth2Response.getName())
//                    .build();
//            userRepository.save(user);
//        } else {
//            user = userOptional.get();
//            user.setUsername(username);
//            user.setEmail(oAuth2Response.getEmail());
//            user.setLoginId(oAuth2Response.getProviderId());
//            user.setProvider(oAuth2Response.getProvider());
//            user.getRoles().add(role);
//            user.setRegistrationDate(LocalDateTime.now());
//            user.setUsernick(oAuth2Response.getName());
//            userRepository.save(user);
//            roleName = user.getRoles().iterator().next().getName();
//        }
//
//        System.out.println("User saved: " + user);
//        return new CustomOAuth2User(oAuth2Response, roleName);
//    }
        return oAuth2User;
    }
}
