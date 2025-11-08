@echo off

REM Compile and run the Spring Boot JSTL project

REM [1] Navigate to the project root (adjust path if needed)
cd /d %~dp0\..

REM [2] Clean and build the project using Maven (ensure mvn is in your PATH)
mvn clean package

IF %ERRORLEVEL% NEQ 0 (
    echo Maven build failed. Exiting.
    exit /b 1
)

REM [3] Run the built Spring Boot jar
REM Replace the JAR name below if it differs (see target\*.jar)
java -jar target\jstl-demo-0.0.1-SNAPSHOT.jar
