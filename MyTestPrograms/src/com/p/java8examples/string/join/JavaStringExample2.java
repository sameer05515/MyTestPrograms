package com.p.java8examples.string.join;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStringExample2 {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("a", "b", "c");

		String result = list.stream().collect(Collectors.joining(","));

		System.out.println(result);

		String[] str = { "AWS", "Adobe Flex 4", "AngularJS 1.1", "AngularJS 1.7", "Apache SOLR", "Bitbucket",
				"Bootstrap", "Bootstrap 3", "CSS", "Collections API", "Core Java", "DB2", "HTML", "Hibernate", "JDBC",
				"JUnit", "Jasper Reports", "JasperReports", "JavaScript", "Jenkins", "Jira", "MySQL",
				"MySQL/SQL Server", "NodeJS", "Oracle", "Oracle 12g", "PostGreSQL", "ReactJS",
				"Restful web services using Jersey", "Servlet/JSP", "Spring Batch", "Spring Boot", "Spring Data",
				"Spring JDBC", "Spring REST", "Spring Rest", "Struts 2", "Talend", "Tomcat", "Ubuntu Linux OS",
				"Weblogic", "Websphere", "Windows OS", "jdbc", "jsp", "servlet", "tomcat", "TypeScript", "Angular 8" };
		
		for(String s:str) {
//			System.out.println("<li>"+s+"</li>");
			System.out.println(s);
		}

	}

}