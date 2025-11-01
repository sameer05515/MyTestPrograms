package com.p.java.playground.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** The entry point for the Spring Boot application. */
@SpringBootApplication
public class ApiApplication {

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints and all origins
        registry
            .addMapping("/**")
            .allowedOrigins("*") // Allow all origins
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow common HTTP methods
            .allowedHeaders("*"); // Allow all headers
        //						.allowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
      }
    };
  }
}
