package com.plan.nest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metric")
public class MetricsController {

    @GetMapping("/test")
    public String metric() {
        return "Spring Boot Monitoring with Prometheus & Grafana";
    }
}
