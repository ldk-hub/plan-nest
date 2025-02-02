package com.plan.nest.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping
    public String metric() {
        PrometheusMeterRegistry prometheusRegistry = (PrometheusMeterRegistry) meterRegistry;
        String prometheusMetrics = prometheusRegistry.scrape();
        return prometheusMetrics;
    }
}
