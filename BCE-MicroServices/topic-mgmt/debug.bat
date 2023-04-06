@echo off
cls
SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_281\

@REM netstat -ano | findstr :8085
@REM taskkill /PID 6260 /F

CALL mvn clean install

call mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"