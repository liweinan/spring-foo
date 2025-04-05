package com.example.springfoo.repository;

import com.example.springfoo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.springframework.data.relational.core.query.Criteria.where;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private R2dbcEntityTemplate template;

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1");
        registry.add("spring.r2dbc.username", () -> "sa");
        registry.add("spring.r2dbc.password", () -> "password");
    }

    @Test
    void testSaveAndFindById() {
        User user = new User("John Doe", "john@example.com");

        StepVerifier.create(userRepository.save(user)
                .flatMap(saved -> userRepository.findById(saved.getId())))
                .expectNextMatches(savedUser -> 
                    savedUser.getName().equals("John Doe") &&
                    savedUser.getEmail().equals("john@example.com") &&
                    savedUser.getId() != null)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        User user1 = new User("John Doe", "john@example.com");
        User user2 = new User("Jane Doe", "jane@example.com");

        StepVerifier.create(userRepository.saveAll(Flux.just(user1, user2))
                .thenMany(userRepository.findAll()))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByEmail() {
        User user = new User("John Doe", "john@example.com");
        
        StepVerifier.create(userRepository.save(user)
                .then(template.select(User.class)
                        .matching(Query.query(where("email").is("john@example.com")))
                        .one()))
                .expectNextMatches(savedUser -> 
                    savedUser.getName().equals("John Doe") &&
                    savedUser.getEmail().equals("john@example.com"))
                .verifyComplete();
    }

    @Test
    void testDelete() {
        User user = new User("John Doe", "john@example.com");

        StepVerifier.create(userRepository.save(user)
                .flatMap(saved -> userRepository.deleteById(saved.getId())
                        .then(Mono.empty())))
                .verifyComplete();
    }
} 