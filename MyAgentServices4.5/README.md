# MyAgentServices 4.5

Spring Boot 2.7 sample application that mixes REST endpoints, JSP-based MVC flows, and legacy MyBatis integrations for managing user and todo data.

## Features
- REST API under `service-controller` exposing user records from MyBatis mappers.
- JSP front end (`login.jsp`, `welcome.jsp`, `list-todos.jsp`) rendered via Spring MVC controllers.
- JavaMelody monitoring and Swagger (Springfox) dependencies included.
- Extensive legacy service layer and helper classes migrated from the original `iAgent` platform.

## Requirements
- JDK 8 (the bundled scripts point to `jdk1.8.0_281`).
- Maven 3.6+.
- MySQL running locally with a database named `resume-db` and accessible credentials that match `spring.datasource.*` in `src/main/resources/application.properties`.
- (Optional) An application server such as Tomcat if you prefer to deploy the generated WAR instead of using the embedded container.

## Getting Started
```bash
# from the project root
mvn clean package

# run with the Spring Boot plugin (uses the embedded Tomcat)
mvn spring-boot:run
```

Windows users can alternatively execute `run.bat`, which performs a clean install before starting the application. Adjust `JAVA_HOME` in the script if your JDK path differs.

The web UI defaults to `http://localhost:8085/login`. REST endpoints such as `GET http://localhost:8085/service-controller/users` provide JSON responses.

## Configuration
- Update database credentials or JDBC URL in `src/main/resources/application.properties`.
- Set any additional MyBatis mapper XML, logging levels, or Swagger configuration in the respective packages (`com.basic.common.access`, `com.commom.logging`, etc.).
- `WebSecurityConfig` currently permits all requests and disables CSRF; tighten these defaults before production use.

## Testing
The only pre-defined test class is `MonitoringApplicationTests` in `src/test/java`. Add targeted tests around controllers and services as you evolve the codebase.

## Troubleshooting
- Ensure the legacy `wsdl4j.jar` referenced in `src/main/webapp/WEB-INF/lib/` is present if you rely on the SOAP tooling modules.
- Verify MySQL connectivity: the default driver alias `com.mysql.jdbc.Driver` requires the 8.x MySQL connector supplied at runtime.

## Next Steps
- Replace deprecated security configuration (`WebSecurityConfigurerAdapter`) with Spring Security's component-based approach.
- Add integration tests validating the MyBatis mappers and REST controllers.
- Document the legacy service modules (`com.ist`, `com.isuite`) if you plan to maintain or extend them.
# MyTestPrograms

# Java Practice Section
- Java concepts are documented [here](readme-files/java.md)



# Some Useful Links
### Spring Boot – CRUD Operations using MongoDB
- https://www.geeksforgeeks.org/spring-boot-crud-operations-using-mongodb/

### JDK 19: The new features in Java 19
https://www.infoworld.com/article/3653331/jdk-19-the-new-features-in-java-19.html

