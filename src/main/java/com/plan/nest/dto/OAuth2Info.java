package com.plan.nest.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class OAuth2Info {

    @Getter
    @Setter
    public static class User{
        private Long id;
        private String email;
        private String nickname;
        private String profileImage;

        @Builder
        public User(Long id, java.lang.String email, java.lang.String nickname, java.lang.String profileImage) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
            this.profileImage = profileImage;
        }

        public static User from(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return User.builder()
                    .id((Long) response.get("id"))
                    .email((String) response.get("email"))
                    .nickname((String) response.get("nickname"))
                    .profileImage((String) response.get("profile_image"))
                    .build();
        }
    }
}
