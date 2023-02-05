# Miscellaneous

## Some References
 - Set title for Java window when running tomcat, https://stackoverflow.com/questions/11952108/set-title-for-java-window-when-running-tomcat
   - In catalina.bat make the following change:
     - ```
         :doStart
          ...
          if "%TITLE%" == "" set TITLE=MY TOMCAT TITLE
       ```
     - where MY TOMCAT TITLE is the title that you want to give to the java window.

## Remote JVM Debug
 - https://stackify.com/java-remote-debugging/


## ShedLock
 - https://github.com/lukas-krecan/ShedLock
 - https://suchit-g.medium.com/use-spring-scheduler-in-clustered-environment-9de27251763f
 - https://faun.pub/spring-scheduler-in-multi-node-environment-49814e031e7c

## Migrating from Spring Security 3.x to 4.x (Java Configuration)
 - https://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-jc.html
 - https://stackoverflow.com/questions/32028024/exception-while-starting-up-the-server-java-lang-nosuchmethodexception-org-sp
   - Using @Scheduled and @EnableScheduling but gives NoSuchBeanDefinitionException
     - https://stackoverflow.com/questions/30431776/using-scheduled-and-enablescheduling-but-gives-nosuchbeandefinitionexception
     - according to exception Info Could not find default TaskScheduler bean, the config should define TaskScheduler rather than "Executor"
     - ```
         @Configuration
         public class AppContext extends WebMvcConfigurationSupport {
         @Bean
         public TaskScheduler taskScheduler() {
         return new ConcurrentTaskScheduler();
         }
    
         // Of course , you can define the Executor too
         @Bean
         public Executor taskExecutor() {
         return new SimpleAsyncTaskExecutor();
         }
         }
         ```
       - Spring Security: Exploring JDBC Authentication, https://www.baeldung.com/spring-security-jdbc-authentication
       - 