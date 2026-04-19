# Carousel with AngularJS (Spring Boot)

AngularJS + UI Bootstrap carousel demo, packaged as **Spring Boot 3.3** with static assets on the classpath and a servlet-style **`/my.jsp`** endpoint preserved for streaming local files or listing a directory as JSON (same behavior as the original JSP).

## Requirements

- **JDK 17+**
- **Maven 3.8+**

## Run

```bash
cd carousel-with-angularjs
mvn spring-boot:run
```

Open **http://localhost:8080/** — the carousel uses sample images from `picsum.photos` so it works without extra servers.

Fat JAR:

```bash
mvn clean package
java -jar target/carousel-with-angularjs-1.0.0-SNAPSHOT.jar
```

## Endpoints

| Path | Purpose |
| --- | --- |
| `/` | `index.html` (Angular carousel) |
| `/css/**`, `/js/**` | Static assets |
| `/my.jsp?documentId=...` | **File:** streams the file with appropriate `Content-Type`. **Directory:** optional `extensions` query params (repeatable) filter files; response body is JSON built by `FileSearcher` (same bracket format as before). |

Example directory listing:

```http
GET /my.jsp?documentId=C:/Users/you/Pictures&extensions=.jpg&extensions=.png
```

Example file:

```http
GET /my.jsp?documentId=C:/Users/you/Pictures/photo.jpg
```

## Configuration

`src/main/resources/application.properties`:

- **`carousel.filesystem.root`** (optional): when set, only paths under this directory are allowed for `/my.jsp` access. Strongly recommended if you expose this app beyond localhost.

If unset, a warning is logged at startup and **any readable path** may be requested (legacy behavior, not safe on a network).

## Project layout

- `com.p.carousel.CarouselApplication` — Boot entry point.
- `com.p.carousel.web.DocumentController` — replaces `my.jsp`.
- `com.p.file.search.FileSearcher` — directory recursion and JSON snippet generation; embed URLs use the current request’s scheme/host/port.

Static UI lives under `src/main/resources/static/` (Bootstrap / Angular / UI Bootstrap loaded from CDN so you do not need a separate server on port 8888).

## Migrating from the old demo

- Replace hard-coded **`http://127.0.0.1:8888/...`** asset URLs with CDN links (already done in `index.html`).
- To point slides at **local images**, set each slide `image` to `/my.jsp?documentId=` + `encodeURIComponent(fullPath)` and configure **`carousel.filesystem.root`** appropriately.

Original CodePen reference: http://codepen.io/Fabiano/pen/LACzk  

See also `license.txt`.
