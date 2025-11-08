# JSTL Spring Boot Port

This module packages the legacy JSTL samples into a Spring Boot 3 project with an embedded Tomcat runtime.

## Project Layout

- `src/main/java` – Spring Boot launcher, MVC controller, and custom tag implementations (migrated to the Jakarta Servlet API).
- `src/main/resources/application.properties` – MVC view resolver configuration.
- `src/main/webapp/WEB-INF/jsp` – JSP views rendered by Spring controllers.
  - `legacy/` – Original sample JSPs retained for reference. These are not wired into the app but can be included or tested manually.
  - `legacy/test/` – Additional exploratory JSPs that were previously under `test/`.
- `src/main/webapp/WEB-INF/jsp/legacy-list.jsp` – Landing page that links to each legacy sample via new controller routes.
- `src/main/webapp/WEB-INF/tlds` – TLD definitions for the custom tags.
- `src/main/resources/static` – Static assets served from the classpath.
  - `example112345/` – Colorbox CSS/images referenced by the legacy samples.
  - `legacy/` – Miscellaneous static artefacts kept for archival purposes.

## Running the Application

```bash
mvn spring-boot:run
```

The main demos are reachable at:

- `http://localhost:8080/` – Quiz home page.
- `http://localhost:8080/numbers` – Number formatting samples.
- `http://localhost:8080/colorbox` – Notes about the legacy Colorbox gallery.
- `http://localhost:8080/legacy` – Index page listing every legacy JSP sample now available through Spring MVC routes.

## Working with Legacy Samples

Legacy JSPs under `WEB-INF/jsp/legacy` are isolated from direct routing. To experiment with them, forward from a controller or copy them into the primary `jsp` folder as needed. Their taglib references now target the shared TLDs under `WEB-INF/tlds`, and supporting static assets live beneath `src/main/resources/static`.

