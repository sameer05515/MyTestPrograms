@echo off
cls
SET JAVA_HOME=C:\cust_inst\Java\jdk-20.0.1
CALL mvn clean install
CALL mvn spring-boot:run