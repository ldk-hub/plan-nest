package com.plan.nest.service;

import com.plan.nest.entity.ServiceUser;
import com.plan.nest.repository.ServiceUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomOauth2UserServiceTest {

    @Mock
    private ServiceUserRepository serviceUserRepository;

    @Mock
    private CustomOauth2UserService customOauth2UserService;


    @Test
    void loadUser_shouldReturnPrincipalDetails_whenNaverLogin() {
        // Mock OAuth2UserRequest and ClientRegistration
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(clientRegistration.getRegistrationId()).thenReturn("naver");
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);

        // ClientRegistration이 null이 아닌지 확인
        assertNotNull(userRequest.getClientRegistration());

        // Mock OAuth2User attributes (네이버에서 주는 id 값 설정)
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", "12345");

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttributes()).thenReturn(attributes);

        //
        ServiceUser mockUser = new ServiceUser("test@naver.com", "TestUser", "naver", "12345");
        when(serviceUserRepository.findByProviderAndProviderId("naver", "12345"))
                .thenReturn(Optional.of(mockUser));

        // ✅ super.loadUser(userRequest) 를 Mock 설정
        when(customOauth2UserService.loadUser(userRequest)).thenReturn(oAuth2User);
        OAuth2User result = customOauth2UserService.loadUser(userRequest);
        assertNotNull(result);
    }
}