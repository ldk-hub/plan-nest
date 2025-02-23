package com.plan.nest.controller;

import com.plan.nest.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final DashBoardService dashBoardService;

}
