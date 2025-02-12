package com.plan.nest.service;

import com.plan.nest.dto.NaverResponse;
import com.plan.nest.dto.OAuth2Response;
import com.plan.nest.entity.PrincipalDetails;
import com.plan.nest.entity.ServiceUser;
import com.plan.nest.repository.ServiceUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService는 OAuth2UserService의 구현체라서
    // DefaultOAuth2UserService 또는 OAuth2UserService 아무거나 상속해도 된다.

    private final ServiceUserRepository serviceUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2Response 객체 생성
        OAuth2Response oAuth2Response = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if ("naver".equals(registrationId)) {
            Map<String, Object> responseMap = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oAuth2Response = new NaverResponse(responseMap);
        }

        if (oAuth2Response == null) {
            throw new OAuth2AuthenticationException("지원되지 않는 OAuth2 공급자입니다.");
        }

        // 기존 유저 조회
        Optional<ServiceUser> serviceUserEntity = serviceUserRepository.findByProviderAndProviderId(
                oAuth2Response.getProvider(), oAuth2Response.getProviderId()
        );

        ServiceUser serviceUser;
        if (serviceUserEntity.isPresent()) {
            serviceUser = serviceUserEntity.get();
            serviceUser.setEmail(oAuth2Response.getEmail());
        } else {
            // 새로운 유저 생성
            serviceUser = ServiceUser.builder()
                    .username(oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId())
                    .email(oAuth2Response.getEmail())
                    .provider(oAuth2Response.getProvider())
                    .providerId(oAuth2Response.getProviderId())
                    .build();
        }
        serviceUserRepository.save(serviceUser);
        return (OAuth2User) new PrincipalDetails(serviceUser, oAuth2User.getAttributes());
    }
}
