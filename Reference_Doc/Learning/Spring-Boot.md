# Spring Boot

## Some Helpful references
 - Spring Boot Reference Documentation, https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
   - Micrometer Documentation, https://micrometer.io/docs
 - How to Change the Default Port in Spring Boot, https://www.baeldung.com/spring-boot-change-port
 - Run Spring boot application using maven , https://docs.spring.io/spring-boot/docs/2.0.6.RELEASE/reference/html/using-boot-running-your-application.html
   - ```
     mvn spring-boot:run
     ```
 - Run Spring boot application using gradle
   - ```
     gradle bootRun
     ```
 - Spring Boot project shows the Login page
   - https://stackoverflow.com/questions/46265775/spring-boot-project-shows-the-login-page
     - You can add a class in your Project, for Web Security configuration as follows:
       - ```
         @Configuration
           @EnableWebSecurity
           public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
         
               @Override
               protected void configure(HttpSecurity http) throws Exception {
                   http
                           .authorizeRequests().antMatchers("/*").permitAll()
                           .and()
                           .csrf().disable();
         
               }
           }
         ```
 - Spring Boot Security Auto-Configuration, https://www.baeldung.com/spring-boot-security-autoconfiguration
   - Spring Security without the WebSecurityConfigurerAdapter, https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 - Spring Boot With JavaServer Pages (JSP), https://www.baeldung.com/spring-boot-jsp
 - An Intro to the Spring DispatcherServlet, https://www.baeldung.com/spring-dispatcherservlet

 - Hot Swapping
   - Since Spring Boot applications are just plain Java applications, JVM hot-swapping should work out of the box. JVM hot swapping is somewhat limited with the bytecode that it can replace. For a more complete solution, JRebel can be used.
   - The spring-boot-devtools module also includes support for quick application restarts. See the Chapter 20, Developer Tools section later in this chapter and the Hot swapping “How-to” for details.
 - ***Apache Tomcat Clustering***
   - https://www.openlogic.com/blog/apache-tomcat-clustering
   - https://tomcat.apache.org/tomcat-9.0-doc/cluster-howto.html
   - https://tomcat.apache.org/tomcat-9.0-doc/config/cluster.html
 - Maven spring boot run debug with arguments
   - https://stackoverflow.com/questions/36217949/maven-spring-boot-run-debug-with-arguments
   - The parameter name has to be prefixed with spring-boot. as in -Dspring-boot.run.jvmArgument 
     - The Spring Boot documentation provided below solution
     - ```
       mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
       ```