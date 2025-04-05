package com.example.springfoo.controller;

import com.example.springfoo.entity.User;
import com.example.springfoo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
public class HelloController {
    
    private final UserRepository userRepository;
    
    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/hello/{name}")
    public Mono<String> hello(@PathVariable String name) {
        return Mono.just(String.format("Hello, %s!", name));
    }
    
    @PostMapping("/users")
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    
    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/users/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }
} 