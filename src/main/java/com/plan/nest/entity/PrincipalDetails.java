package com.plan.nest.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails {
    private final ServiceUser serviceUser;
    private final Map<String, Object> attributes;

    public PrincipalDetails(ServiceUser serviceUser, Map<String, Object> attributes) {
        this.serviceUser = serviceUser;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();  // 권한 설정 필요 시 수정
    }

    @Override
    public String getPassword() {
        return null; // OAuth2 로그인에서는 비밀번호가 필요 없음
    }

    @Override
    public String getUsername() {
        return "";
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}