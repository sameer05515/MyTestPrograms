# Getting Started: To add unit testing and coverage capability

# Getting Started: To add integration between spring-boot and swagger-ui

This application uses `springdoc-openapi v2.7.0` java library that helps to automate the generation of API documentation
using spring boot projects.

[springdoc-openapi documentation link](https://springdoc.org/#getting-started)

For the integration between spring-boot and swagger-ui, add the library to the list of your project dependencies (No
additional configuration is needed)

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

This will automatically deploy swagger-ui to a spring-boot application:

- Documentation will be available in HTML format, using the
  official [swagger-ui jars](https://github.com/swagger-api/swagger-ui.git)
  The Swagger UI page will then be available at `http://localhost:8080/swagger-ui.html` and the OpenAPI
  description will be available at the following url for json format: `http://localhost:8080/v3/api-docs`
    - server: The server name or IP
    - port: The server port
    - context-path: The context path of the application

- Documentation can be available in yaml format as well, on the following path :
  `http://localhost:8080/v3/api-docs.yaml`