# consumer.rest.flux

Reactive counterpart to **`consume.rest`**: fetches GitHub repository pages with **`WebClient`** (Spring WebFlux), merges JSON with **Gson**, and writes the same HTML table layout to a configurable file.

## vs `consume.rest`

| | consume.rest | consumer.rest.flux |
| --- | --- | --- |
| HTTP client | `RestTemplate` | `WebClient` |
| Calls | Blocking loop | `Flux.concatMap` + reactive chain |
| Boot | 2.6.x, Java 8, WAR-capable | 3.3.x, Java 17, standalone JAR |
| Output path | Hard-coded Windows path | `app.output.html` (property) |

The original project always printed **`1`** in the `#` column; this version increments the row number correctly.

## Run

Requires outbound HTTPS to `api.github.com` (GitHub suggests a `User-Agent` header; this app sends `consumer.rest.flux`).

```bash
cd consumer.rest.flux
mvn spring-boot:run
```

HTML is written to **`output/aa.html`** under the current working directory by default (see `application.properties`).

```bash
mvn clean package
java -jar target/consumer.rest.flux-0.0.1-SNAPSHOT.jar
```

## Configuration

`src/main/resources/application.properties`:

- **`spring.main.web-application-type=none`** — no embedded HTTP server; only the REST client runs.
- **`app.github.user`** — GitHub username (default `sameer05515`, matching the original sample).
- **`app.github.max-page`** — last page index to fetch (default `3`; pages `1..max-page`).
- **`app.output.html`** — output file path (default `output/aa.html`).
- **`app.run-on-startup`** — when `false`, the GitHub fetch is skipped (used in tests; default `true`).

## Troubleshooting

**`DataBufferLimitException: Exceeded limit on max bytes to buffer : 262144`** — GitHub JSON can be larger than WebClient’s default **256KB** in-memory buffer. This project sets **`spring.codec.max-in-memory-size=10MB`** and applies it on the `WebClient` `ExchangeStrategies`. Increase further if needed.

## License

Same as parent repo / your choice.
