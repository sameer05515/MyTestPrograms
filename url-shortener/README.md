# High-Scale URL Shortener

Spring Boot URL shortener service with:

- URL shortening and redirect
- JWT authentication
- In-memory + concurrency-aware rate limiting
- Caffeine caching for short-code lookups
- JPA entities and indexed queries for DB efficiency

## Run

```bash
mvn spring-boot:run
```

## API

### Auth

- `POST /api/auth/register`
- `POST /api/auth/login`

### URL APIs (JWT required)

- `POST /api/urls`
- `GET /api/urls/{shortCode}/stats`

### Redirect (public)

- `GET /{shortCode}`

## Sample payloads

Register:

```json
{
  "username": "alice",
  "password": "password123"
}
```

Shorten URL:

```json
{
  "longUrl": "https://spring.io/projects/spring-boot",
  "expiryDays": 30
}
```

## Notes

- Change `app.jwt.secret` in `application.yml` for production.
- H2 console is available at `/h2-console`.
- `UrlMapping` uses indexes on short code, owner+hash, and expiry for faster lookups.
