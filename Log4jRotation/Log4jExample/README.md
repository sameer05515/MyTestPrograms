# Log4j Example — Spring Boot Edition

This project modernises the original Log4j rotation demo by turning it into a Spring Boot application that uses Log4j2 for rolling log files.

## Features

- Spring Boot 3 application structure with Maven build.
- REST endpoint `POST /api/logs` to create log entries on demand.
- Configurable scheduled emitter that generates log messages at a fixed interval.
- Log4j2 rolling-file configuration that keeps the latest five archives (time + size based policies).
- Actuator starter included for future operational endpoints.

## Prerequisites

- Java 17 or later
- Maven 3.9+

## Running the App

```bash
mvn spring-boot:run
```

Logs are written to the console and to `logs/example.log`. Archived files live in `logs/archive`.

## Triggering Logs Manually

```bash
curl -X POST http://localhost:8080/api/logs \
     -H "Content-Type: application/json" \
     -d '{"message":"Hello from cURL","level":"debug"}'
```

Accepted levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `FATAL`. The field is optional and defaults to `INFO`.

## Configuration

Adjust values in `src/main/resources/application.properties`:

- `logging.demo.enabled` — enable/disable the scheduled emitter.
- `logging.demo.interval-ms` — delay between log entries.
- `logging.demo.message` — message emitted by the scheduler.

Modify `src/main/resources/log4j2-spring.xml` to adapt appenders, patterns, or rollover strategy.

