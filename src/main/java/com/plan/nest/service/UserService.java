package com.plan.nest.service;

import com.plan.nest.dto.OAuth2Info;
import com.plan.nest.entity.ServiceUser;
import com.plan.nest.repository.ServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ServiceUserRepository serviceUserRepository;

    @Transactional
    public ServiceUser saveOrUpdateUser(OAuth2Info.User oAuth2UserInfo) {
        ServiceUser user = serviceUserRepository.findById(oAuth2UserInfo.getId())
                .map(existingUser -> {
                    existingUser.setNickname(oAuth2UserInfo.getNickname());
                    return existingUser;
                })
                .orElse(ServiceUser.builder()
                        .id(oAuth2UserInfo.getId())
                        .email(oAuth2UserInfo.getEmail())
                        .nickname(oAuth2UserInfo.getNickname())
                        .build());

        return serviceUserRepository.save(user);
    }
}
