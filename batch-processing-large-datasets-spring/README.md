# batch-processing-large-datasets-spring

Minimal **Spring Boot** app that demonstrates **Spring Batch** reading a CSV of voltage/time samples and loading them into a database with chunked processing, JPA for the domain model, and an in-memory **H2** database.

**Coordinates:** `com.techshard.batch:springboot-batch:1.0-SNAPSHOT`  
**Spring Boot:** 2.1.6.RELEASE (Java 8 compatible via the Boot parent)

## What it does

1. On startup, Spring Batch runs the job **`importVoltageJob`** (default `spring.batch.job.enabled=true` in Spring Boot 2.x).
2. **`Volts.csv`** on the classpath is read as **semicolon-delimited** rows with columns `volt` and `time`.
3. Each chunk of **10** records is passed through **`VoltageProcessor`** (currently a pass-through that copies `volt` and `time`).
4. **`JdbcBatchItemWriter`** inserts into the **`voltage`** table: `INSERT INTO voltage (volt, time) VALUES (:volt, :time)`.
5. **`NotificationListener`** runs after a **COMPLETED** job and logs all rows read back via **`JdbcTemplate`**.

Spring Batch also creates its own metadata tables in the same datasource.

## Requirements

- **JDK 8+** (aligned with Spring Boot 2.1 parent)
- **Maven 3.x**

## Run

From this project directory:

```bash
mvn spring-boot:run
```

Or:

```bash
mvn clean package
java -jar target/springboot-batch-1.0-SNAPSHOT.jar
```

Watch the console for batch lifecycle logs, `TracePerformanceAspect` timing lines for `com.techshard..*` methods, and `NotificationListener` output after the job completes.

## Configuration

`src/main/resources/application.properties`:

| Property | Purpose |
| --- | --- |
| `spring.datasource.*` | In-memory H2 (`jdbc:h2:mem:batchdb`), user `sa` / `password` |
| `spring.jpa.database-platform` | `H2Dialect` |
| `spring.h2.console.enabled=true` | H2 web console (Boot default path is `/h2-console`) |

To **disable** automatic job launch on startup (e.g. for tests or REST-triggered jobs), set:

```properties
spring.batch.job.enabled=false
```

## Project layout

| Area | Contents |
| --- | --- |
| `Application.java` | Spring Boot entry point; extends `SpringBootServletInitializer` for WAR-style deployment if needed |
| `configuration/BatchConfiguration.java` | `@EnableBatchProcessing`, reader / processor / writer, `importVoltageJob`, `step1` |
| `configuration/VoltageFieldSetMapper.java` | Maps CSV `FieldSet` → `Voltage` (`BigDecimal` volt, `double` time) |
| `configuration/VoltageProcessor.java` | `ItemProcessor<Voltage, Voltage>` |
| `configuration/NotificationListener.java` | `JobExecutionListenerSupport` — post-job verification query |
| `dao/entity/Voltage.java` | JPA entity (`volt`, `time`, generated `id`) |
| `dao/repository/IVoltageRepository.java` | `JpaRepository` (available for queries; the batch **writer** uses JDBC, not this repository) |
| `TracePerformanceAspect.java` | `@Around` on `com.techshard..*` — logs method execution time |
| `resources/Volts.csv` | Sample input (`volt;time` per line) |

## Input file format

`Volts.csv` uses **`;`** as the delimiter, for example:

```text
9.2128;0
8.7952;0.02
```

Field names expected by the reader/tokenizer: **`volt`**, **`time`**.

## Dependencies (high level)

- `spring-boot-starter-batch` — Spring Batch
- `spring-boot-starter-data-jpa` — `Voltage` entity and `IVoltageRepository`
- `spring-boot-starter-web` — Servlet stack (no REST controllers in this sample)
- `spring-boot-starter-aop` — `TracePerformanceAspect`
- `h2` (runtime) — embedded database

## Extending the sample

- Point the reader at another `Resource` or add job parameters for the file path.
- Add validation or transformation in `VoltageProcessor`.
- Use `IVoltageRepository` or a service layer for reporting instead of raw `JdbcTemplate` in the listener.
- Add integration tests with `spring.batch.job.enabled=false` and a test `JobLauncher`.

## License

See `LICENSE` in this directory.
