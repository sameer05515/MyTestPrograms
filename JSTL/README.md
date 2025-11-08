# JSTL Spring Boot Port

This module packages the legacy JSP/JSTL showcase into a Spring Boot 3 application that runs on the embedded Tomcat container. It preserves the original custom tags and sample pages while providing a modern build/run experience.

## Project Layout

- `src/main/java`
  - `com.prem.jstl.JstlDemoApplication` – Spring Boot launcher.
  - `com.prem.jstl.web.*` – Controllers for modern and legacy sample pages.
  - `com.prem.tags.*` – Custom tag implementations refactored to Jakarta Servlet APIs.
- `src/main/resources/application.properties` – Configures the JSP view resolver.
- `src/main/webapp/WEB-INF/jsp`
  - Primary JSP views (`home.jsp`, `numFormat.jsp`, `quiz-result.jsp`, `colorbox.jsp`).
  - `legacy/` – Archived JSP samples; now accessible via `/legacy/**` routes.
  - `legacy/test/` – Additional exploratory JSPs migrated from the former `test/` folder.
  - `legacy-list.jsp` – Index page that links to every legacy sample.
- `src/main/webapp/WEB-INF/tlds` – TLD definitions consumed by both the modern and legacy JSPs.
- `src/main/resources/static`
  - `example112345/` – Styles/images required by the Colorbox demonstrations.
  - `legacy/` – Miscellaneous static artefacts preserved for reference (e.g. `home.html`).

## Running the Application

```bash
mvn spring-boot:run
```

Or build a runnable jar:

```bash
mvn clean package
java -jar target/jstl-demo-0.0.1-SNAPSHOT.jar
```

### Entry Points

- `http://localhost:8080/` – Quiz home page.
- `http://localhost:8080/numbers` – Number formatting samples.
- `http://localhost:8080/colorbox` – Notes about the legacy Colorbox gallery and migration considerations.
- `http://localhost:8080/legacy` – Index page listing every legacy JSP sample available through Spring MVC routes.

### Hitting Individual Legacy Pages

The `/legacy` controller exposes each archived JSP. Example routes:

| Path | Original JSP |
| ---- | ------------ |
| `/legacy/subscript` | `legacy/Subscript.jsp` |
| `/legacy/test-page` | `legacy/TestPage.jsp` |
| `/legacy/number-format` | `legacy/numFormat-legacy.jsp` |
| `/legacy/colorbox` | `legacy/colorbox-legacy.jsp` |
| `/legacy/test/colorbox-explorer` | `legacy/test/colorboxExplorer.jsp` |

## Working with Legacy Samples

Legacy JSPs under `WEB-INF/jsp/legacy` retain their original markup. Keep the following in mind:

- Some pages reference local file-system paths (e.g. `C:\...\` in Colorbox demos). Update `base`, `baseURLPrefix`, and similar attributes to suit your environment.
- External resources loaded from `http://127.0.0.1:8888/...` are placeholders from the original project. Swap them for CDN or local assets as needed.
- All tag library URIs now resolve to `/WEB-INF/tlds/*.tld`; no changes are required to reuse the custom tags.
- Because legacy pages sit under `WEB-INF`, they are not directly accessible over HTTP without an explicit controller mapping—`LegacyPageController` handles this for you.

## Requirements

- JDK 17+
- Maven 3.8+

The project uses Spring Boot 3.x, which depends on the Jakarta EE 10 APIs. Ensure any additional JSP/tag libraries you add are compatible with Jakarta namespaces.

## Contributing

Feel free to adapt or modernize the legacy samples:

- Add new controllers and views under `src/main/java` and `src/main/webapp/WEB-INF/jsp`.
- Migrate hard-coded filesystem logic to Spring services, or externalize paths via `application.properties`.
- Write tests under `src/test/java` to cover custom tag behaviour or controller responses.

