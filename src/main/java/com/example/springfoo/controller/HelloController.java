package com.example.springfoo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    
    @GetMapping("/hello/{name}")
    public Mono<String> hello(@PathVariable String name) {
        return Mono.just(String.format("Hello, %s!", name));
    }
} 