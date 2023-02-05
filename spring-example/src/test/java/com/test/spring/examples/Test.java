package com.test.spring.examples;

import com.test.spring.examples.pojo.Student;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;  
import org.springframework.core.io.ClassPathResource;  
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

public class Test {  
public static void main(String[] args) {  
    Resource resource=new ClassPathResource("applicationContext.xml");  
    BeanFactory factory=new XmlBeanFactory(resource);  
      
    Student student=(Student)factory.getBean("studentbean");
    student.displayInfo();

    DataSource ds= (DataSource) factory.getBean("dataSource");
    System.out.println(ds);
}  
}  