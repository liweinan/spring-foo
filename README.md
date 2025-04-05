# Spring Foo - Reactive REST API

A Spring Boot application demonstrating reactive programming with Spring WebFlux, R2DBC, and H2 database.

## Features

- Reactive REST API using Spring WebFlux
- Reactive database access with R2DBC and H2
- User management endpoints
- Comprehensive test coverage
- In-memory H2 database for development

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Running the Application

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### API Endpoints

#### Greeting Endpoint
- `GET /hello/{name}`
  - Returns a greeting message
  - Example: `GET /hello/John` returns "Hello, John!"

#### User Management Endpoints
- `POST /users`
  - Create a new user
  - Request body:
    ```json
    {
        "name": "John Doe",
        "email": "john@example.com"
    }
    ```

- `GET /users`
  - Get all users
  - Returns a list of users

- `GET /users/{id}`
  - Get user by ID
  - Returns a single user or 404 if not found

### H2 Database Console

The H2 database console is available at `http://localhost:8080/h2-console`

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Testing

The project includes comprehensive test coverage:

### Running Tests

```bash
mvn test
```

### Test Coverage

1. Controller Tests (`HelloControllerTest`)
   - Tests all REST endpoints
   - Uses WebTestClient for reactive endpoint testing
   - Mocks repository layer

2. Repository Tests (`UserRepositoryTest`)
   - Integration tests with H2 database
   - Tests CRUD operations
   - Uses StepVerifier for reactive stream testing

3. Test Configuration (`TestConfig`)
   - Provides test-specific configuration
   - Sets up test database

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/springfoo/
│   │       ├── SpringFooApplication.java
│   │       ├── controller/
│   │       │   └── HelloController.java
│   │       ├── entity/
│   │       │   └── User.java
│   │       └── repository/
│   │           └── UserRepository.java
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── test/
    └── java/
        └── com/example/springfoo/
            ├── config/
            │   └── TestConfig.java
            ├── controller/
            │   └── HelloControllerTest.java
            └── repository/
                └── UserRepositoryTest.java
```

## Dependencies

- Spring Boot 3.2.3
- Spring WebFlux
- Spring Data R2DBC
- H2 Database
- R2DBC H2 Driver

## License

This project is licensed under the MIT License.
