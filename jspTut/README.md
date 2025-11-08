# JSP Tutorial — Spring Boot Edition

This project modernizes a legacy JSP tutorial into a Spring Boot 3 application while preserving the original examples for reference.

## Highlights
- Embedded Tomcat via Spring Boot (no external container needed)
- JSP views served from src/main/webapp/WEB-INF/jsp
- Legacy pages archived under WEB-INF/jsp/legacy
- JSTL, XSLT, custom tags, form handling, and scriptlet-to-JSTL conversions demonstrated

## Project Structure
```
jspTut/
├── pom.xml                       # Maven build configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/jsptut/    # Spring Boot app, controllers, services, utilities
│   │   │   ├── com/journaldev/        # Legacy servlet/sample classes
│   │   │   ├── com/tutorialspoint/    # Legacy filters, beans, custom tag classes
│   │   │   └── user/, util/           # Additional legacy beans/constants
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── static/data/           # XML + XSL assets for JSTL demos
│   │   └── webapp/WEB-INF/jsp/
│   │      ├── index.jsp               # Spring Boot landing page listing demos
│   │      ├── jstl/, xml/, forms/, samples/
│   │      └── legacy/                 # Archived pre-migration JSPs grouped by topic
│   └── test/
└── README.md
```

## Prerequisites
- JDK 17+
- Maven 3.9+

## Getting Started
```bash
mvn spring-boot:run
```
Visit http://localhost:8080 to access the demo index.

### Alternative
```bash
mvn clean package
java -jar target/jsp-tut-0.0.1-SNAPSHOT.jar
```

## Notable Endpoints
- /jstl/home – JSTL core tags with EL-backed data
- /xml/import – JSTL XML parsing from static/data/books.xml
- /xml/transform – XSLT transformations using style.xsl
- /custom/hello – Custom tag invocation (custom.tld, HelloTag)
- /forms/name – Session-backed form flow using Spring MVC
- /samples/scriptlet – Scriptlet demo converted to JSTL/EL

## Legacy Archive
The original JSPs are preserved under src/main/webapp/WEB-INF/jsp/legacy. They are not wired into the Spring Boot controllers by default but can be inspected or re-enabled as needed.

## Development Notes
- Controllers expose data for each JSP to minimize scriptlet usage.
- Legacy filters & servlets compile against jakarta.servlet APIs for compatibility with Spring Boot 3.
- Static XML/XSL assets now live in src/main/resources/static/data and are referenced by the JSPs via context-relative URLs.

## Next Steps
- Gradually refactor remaining legacy pages if you plan to integrate them into Spring MVC controllers.
- Add integration tests around key controllers if you intend to evolve the project further.
