package com.p.problem.mgmt.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProblemMgmtNosqlApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProblemMgmtNosqlApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigureBean() {
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
