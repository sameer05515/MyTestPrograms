# Log4J Package-Specific Logging Demo

## Overview
This Maven project demonstrates how to configure **Apache Log4j 1.x** so that:
- the application writes to a root log file, and
- classes under a specific package (`com.newpackage`) route their messages to a separate rolling file appender.

`MainClass` continuously calls two helper classes (`SpecialPackageClass_1` and `_2`) to generate log traffic for both appenders.

## Project Layout
- `src/main/java/com/SeparateLoggerTest/MainClass.java` – entry point that emits log statements.
- `src/main/java/com/newpackage/*.java` – classes whose loggers resolve to the package-specific appender.
- `src/main/resources/log4j.properties` – Log4j configuration defining the root and package appenders.
- `pom.xml` – Maven build descriptor targeting Java 8 with Log4j 1.2.x.

## Prerequisites
- JDK 8 or later
- Maven 3.8+

## Build and Run
```bash
mvn clean package
java -cp target/Log4JPackgSpecificLog-1.0-SNAPSHOT.jar com.SeparateLoggerTest.MainClass
```

> **Note:** `MainClass` contains an infinite loop that continually logs messages. Stop the program with `Ctrl+C` after you have captured enough log output.

## Log Output
By default the configuration writes to two rolling files (1 MB max, 5 backups):
- Root logger: `C:\Users\premendra.kumar\Desktop\log-output\mainlog.log`
- Package logger (`com.newpackage`): `C:\Users\premendra.kumar\Desktop\log-output\separatepackage.log`

Update the `log4j.appender.*.File` paths in `log4j.properties` to match your environment before running the demo.

## Customisation Tips
- Adjust log levels per package by changing `log4j.logger.com.newpackage=<LEVEL>, packageLogger`.
- Add more package-specific appenders for other namespaces as needed.
- Consider introducing a finite loop or a scheduled executor if you want controlled log generation.

## Cleaning the Build
```bash
mvn clean
```

