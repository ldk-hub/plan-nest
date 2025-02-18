package com.plan.nest.service;

import com.plan.nest.entity.ServiceUser;
import com.plan.nest.repository.ServiceUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomOauth2UserServiceTest {

    @Mock
    private ServiceUserRepository serviceUserRepository;

    @InjectMocks
    private CustomOauth2UserService customOauth2UserService;

    @Test
    void loadUser_shouldReturnPrincipalDetails_whenNaverLogin() {
        // Mock OAuth2UserRequest and ClientRegistration
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        when(clientRegistration.getRegistrationId()).thenReturn("naver");
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);

        // Mock OAuth2User
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttributes()).thenReturn(new HashMap<>());

        // Mock ServiceUserRepository behavior
        ServiceUser mockUser = new ServiceUser("test@naver.com", "TestUser", "naver", "12345");
        when(serviceUserRepository.findByProviderAndProviderId("naver", "12345"))
                .thenReturn(Optional.of(mockUser));

        // 3. 실제 서비스에서 loadUser 호출 시 super.loadUser 호출을 통해 결과 얻기
        OAuth2User result = customOauth2UserService.loadUser(userRequest);  // `super.loadUser(userRequest)`가 내부에서 호출됩니다.

        // 4. 결과 검증
        assertNotNull(result);  // 결과가 null이 아닌지 확인
    }
}