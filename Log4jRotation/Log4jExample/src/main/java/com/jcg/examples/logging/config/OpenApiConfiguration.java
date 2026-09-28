package com.jcg.examples.logging.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfiguration {

	@Bean
	OpenAPI loggingOpenApi(@Value("${spring.application.name:Log4j Example}") String applicationName) {
		return new OpenAPI()
			.info(new Info()
				.title("Log4j Example API")
				.description("Spring Boot service showcasing Log4j2 rolling configuration and logging APIs.")
				.version("1.0.0")
				.contact(new Contact().name("Example Team").email("support@example.com"))
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
			.externalDocs(new ExternalDocumentation()
				.description(applicationName + " README")
				.url("https://example.com/docs/log4j-example"));
	}
}

