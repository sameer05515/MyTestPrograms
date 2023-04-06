@echo off
cls
SET JAVA_HOME=C:\cust_inst\Java\jdk-20.0.1

CALL mvn clean install

call mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"