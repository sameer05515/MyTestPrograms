package com.p.servlet.ex;

import com.p.servlet.ex.filter.LogFilter;
import com.p.servlet.ex.servlets.AddNumbersServletV1;
import com.p.servlet.ex.servlets.AddNumbersServletV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServletExamplesApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServletExamplesApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<AddNumbersServletV1> addNumbersV1ServletRegistration() {
		ServletRegistrationBean<AddNumbersServletV1> registrationBean
				= new ServletRegistrationBean<>(new AddNumbersServletV1(), "/v1/add/*");
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean<AddNumbersServletV2> addNumbersV2ServletRegistration() {
		ServletRegistrationBean<AddNumbersServletV2> registrationBean
				= new ServletRegistrationBean<>(new AddNumbersServletV2(), "/v2/add");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<LogFilter> loggingFilter() {
		FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new LogFilter());
		registrationBean.addUrlPatterns("/*"); // Filter will apply to all URL patterns

		// Optionally set init parameters
		registrationBean.addInitParameter("test-param", "Premendra Kumar");

		return registrationBean;
	}

}
