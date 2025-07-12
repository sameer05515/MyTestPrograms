@echo off
cls
@REM SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_281\
CALL mvn clean install
CALL mvn spring-boot:run