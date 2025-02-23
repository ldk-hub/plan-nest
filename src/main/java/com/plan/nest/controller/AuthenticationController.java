package com.plan.nest.controller;

import com.plan.nest.repository.ServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final ServiceUserRepository serviceUserRepository;

}
