LoginSecurity
=============

Overview
--------
LoginSecurity is a starter Spring Boot (2.3.x) project configured for experimenting with Spring Security, Spring Data JPA, and Spring Data REST. The current code base is intentionally minimal: it boots the application context but does not yet define domain models, repositories, controllers, or any security policies. Use it as a foundation for building and testing custom authentication and authorization flows.

Tech Stack
----------
- Java 11
- Spring Boot 2.3.2
- Spring Security
- Spring Data JPA & Spring Data REST
- Maven (wrapper included)

Project Layout
--------------
- `src/main/java/com/p/security/LoginSecurity/LoginSecurityApplication.java` — Spring Boot entry point.
- `src/main/resources/application.properties` — Placeholder for application configuration (currently empty).
- `src/test/java/.../LoginSecurityApplicationTests.java` — Context load smoke test scaffold.
- `pom.xml` — Dependency and plugin definitions.

Getting Started
---------------
1. **Prerequisites**
   - Java 11+
   - Maven 3.6+ (or use the included `mvnw`/`mvnw.cmd` wrapper)

2. **Build & Run**
   ```bash
   # from the project root
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. **Run Tests**
   ```bash
   ./mvnw test
   ```

Configuration
-------------
Add your datasource, JPA, and security properties to `src/main/resources/application.properties`. Example:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/loginsecurity
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
logging.level.org.springframework.security=DEBUG
```

Next Steps
----------
- Model the domain (e.g., `User`, `Role`) and create JPA repositories.
- Introduce a `UserDetailsService` and password encoder.
- Configure Spring Security (`WebSecurityConfigurerAdapter` or component-based security configuration for newer Spring versions).
- Add integration tests covering authentication and authorization scenarios.

License
-------
No license information is provided. Add one before distributing the project.

