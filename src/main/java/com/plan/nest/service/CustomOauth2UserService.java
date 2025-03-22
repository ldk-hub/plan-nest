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

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final ServiceUserRepository serviceUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = extractOAuth2Response(registrationId, oAuth2User);
        if (oAuth2Response == null) {
            log.error("에러 발생 : {}", registrationId);
            throw new OAuth2AuthenticationException("지원되지 않는 OAuth2 공급자입니다.");
        }

        ServiceUser serviceUser = findOrCreateUser(oAuth2Response);
        return (OAuth2User) new PrincipalDetails(serviceUser, oAuth2User.getAttributes());
    }

    private OAuth2Response extractOAuth2Response(String registrationId, OAuth2User oAuth2User) {
        if ("naver".equals(registrationId)) {
            Map<String, Object> responseMap = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            return new NaverResponse(responseMap);
        }
        throw new OAuth2AuthenticationException("지원되지 않는 OAuth2 공급자: " + registrationId);
    }

    private ServiceUser findOrCreateUser(OAuth2Response oAuth2Response) {
        return serviceUserRepository.findByProviderAndProviderId(oAuth2Response.getProvider(), oAuth2Response.getProviderId())
                .map(user -> {
                    user.setEmail(oAuth2Response.getEmail());
                    return user;
                })
                .orElseGet(() -> {
                    ServiceUser newUser = ServiceUser.builder()
                            .nickname(oAuth2Response.getNickName() + "_" + oAuth2Response.getProviderId())
                            .email(oAuth2Response.getEmail())
                            .provider(oAuth2Response.getProvider())
                            .providerId(oAuth2Response.getProviderId())
                            .build();
                    return serviceUserRepository.save(newUser);
                });
    }
}