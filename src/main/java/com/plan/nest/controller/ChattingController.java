package com.plan.nest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/chat")
public class ChattingController {

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("Hello, WebFlux!");
    }

    @GetMapping("/numbers")
    public Flux<Integer> numbers(){
        return Flux.just(1,2,3,4,5);
    }

    @GetMapping("/delayed")
    public Mono<String> delayTest(){
        return Mono.just("third second delay chat")
                .delayElement(Duration.ofSeconds(3));
    }
}
