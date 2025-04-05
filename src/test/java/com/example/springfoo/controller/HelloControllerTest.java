package com.example.springfoo.controller;

import com.example.springfoo.entity.User;
import com.example.springfoo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testHelloEndpoint() {
        webTestClient.get()
                .uri("/hello/John")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo("Hello, John!");
    }

    @Test
    public void testCreateUser() {
        User user = new User("John Doe", "john@example.com");
        User savedUser = new User("John Doe", "john@example.com");
        savedUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(savedUser));

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("John Doe")
                .jsonPath("$.email").isEqualTo("john@example.com");
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("John Doe", "john@example.com");
        user1.setId(1L);
        User user2 = new User("Jane Doe", "jane@example.com");
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .contains(user1, user2);
    }

    @Test
    public void testGetUserById() {
        User user = new User("John Doe", "john@example.com");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("John Doe")
                .jsonPath("$.email").isEqualTo("john@example.com");
    }

//    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/users/999")
                .exchange()
                .expectStatus().isNotFound();
    }
} 