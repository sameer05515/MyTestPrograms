# Monitoring

A Spring Boot 2.7 demo that mixes classic Spring MVC with JSP views, MyBatis mappers and JavaMelody application monitoring. The sample exposes a simple login + todo workflow and a set of reporting endpoints that rely on MyBatis queries to inspect application activity.

## Project Structure
- `src/main/java/com` – Spring Boot entry point, MVC controllers, security config.
- `src/main/java/com/basic|commom` – Cross-cutting helpers (application context access, logging aspects, MyBatis-driven reporting services).
- `src/main/java/com/ils` – MyBatis mapper interfaces.
- `src/main/webapp/WEB-INF/views` – JSP templates rendered by the controllers.
- `src/main/resources/application.properties` – Environment-specific overrides, e.g. port, datasource.

## Prerequisites
- JDK 8+
- Maven 3.8+ (wrapper `mvnw` is included)
- MySQL 5.7+/8.x with a database named `test`
- A user granted read/write access to the schema

## Configuration
Adjust `src/main/resources/application.properties` to point to your database host and credentials. When using MySQL 8.x keep the driver set to `com.mysql.cj.jdbc.Driver`.

```
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
spring.datasource.username=<user>
spring.datasource.password=<password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Additional profiles can be supplied via `spring.profiles.active`.

## Running the Application

Build the WAR (the default packaging) and run it with the embedded container:

```
.\mvnw clean package
java -jar target\monitoring-0.0.1-SNAPSHOT.war
```

Or start it directly from Maven during development:

```
.\mvnw spring-boot:run
```

The app listens on port `8085` by default.

## Key Features
- Form-based login backed by a simple in-memory `LoginService`.
- Todo list rendered via JSP (`/list-todos`).
- MyBatis-backed reporting endpoints under `ApplicationLoggingReportController`.
- JavaMelody monitoring servlet wired through `net/bull/javamelody` configuration.

## Testing
Run the unit test suite with:

```
.\mvnw test
```

## Troubleshooting
- Ensure the database schema contains the tables referenced by the MyBatis mappers (e.g. `test`, `application_log`, `employee_master`, etc.).
- If you see `ClassNotFoundException: com.mysql.jdbc.Driver`, update the driver class to `com.mysql.cj.jdbc.Driver` as shown above.
- When deploying to an external servlet container, remember the project packages as a WAR file; update the target server context path as needed.


