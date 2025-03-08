package com.plan.nest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class ServiceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nickname;

    @Setter
    @Column(name="email")
    private String email;

    @Column(name= "provider")
    private String provider;

    @Column(name= "provider_id")
    private String providerId;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @Builder
    public ServiceUser(Long id, String nickname, String email, String provider, String providerId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }


}