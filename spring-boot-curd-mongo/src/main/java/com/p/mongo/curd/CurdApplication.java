package com.p.mongo.curd;

import com.p.mongo.curd.controller.GetFileServlet;
import com.p.mongo.curd.controller.MyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CurdApplication {

	// Register Servlet
	@Bean
	public ServletRegistrationBean<MyServlet> servletRegistrationBean() {
		ServletRegistrationBean<MyServlet> bean = new ServletRegistrationBean(
				new MyServlet(), "/myServlet");
		return bean;
	}

	// Register Servlet
	@Bean
	public ServletRegistrationBean<GetFileServlet> servletRegistrationBean2() {
		ServletRegistrationBean<GetFileServlet> bean = new ServletRegistrationBean(
				new GetFileServlet(), "/getFileServlet");
		return bean;
	}

	public static void main(String[] args) {
		SpringApplication.run(CurdApplication.class, args);
	}

}
