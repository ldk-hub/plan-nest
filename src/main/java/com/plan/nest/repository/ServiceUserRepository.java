package com.plan.nest.repository;

import com.plan.nest.entity.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {
    Optional<ServiceUser> findByUsername(String username);

    Optional<ServiceUser> findByProviderAndProviderId(String provider, String providerId);
}
