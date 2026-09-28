# Logback with Spring Boot Configuration

This sample Spring Boot application demonstrates how to wire Logback appenders, rolling policies, and profile specific logging behaviour. It schedules a recurring task that exercises the logging stack so you can inspect the emitted files/console output.

## Prerequisites
- JDK 8 or newer
- Maven 3.6+

## Getting Started
```bash
mvn spring-boot:run
```

By default the `dev` profile is active (`spring.profiles.active=dev` in `application.properties`). The application prints a heartbeat message every minute and invokes `MyServiceImpl#doStuff`, which logs at all levels.

To launch with another profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Logging Overview
- `logback-spring.xml` configures appenders and profile specific loggers.
- `logback.xml` contains alternative rolling strategies that you can toggle for experimentation.
- Log files are written under `logs/` relative to the project root.
- Active profile determines which appenders and levels are enabled:
  - **dev**: console + file appender, `MyServiceImpl` at `DEBUG`, root at `INFO`.
  - **prod**: file appender only, `MyServiceImpl` at `ERROR`, root at `INFO`.

### Scheduled job
The `Application` class is annotated with `@EnableScheduling`. A cron expression (`console.cronExpression`) defined in `application.properties` controls how often the job runs.

## Key Files
- `src/main/java/com/lankydan/Application.java` – Spring Boot entry point and scheduled task.
- `src/main/java/com/lankydan/service/MyServiceImpl.java` – Emits log statements at all levels.
- `src/main/resources/logback-spring.xml` – Profile aware Logback configuration.
- `src/main/resources/logback.xml` – Rolling appender examples.

## Useful Maven Commands
- `mvn clean package` – Build the jar.
- `mvn spring-boot:run` – Run the application with the active profile.


