# Command to start spring boot application in debug mode

```shell
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 .\target\node-service-0.0.1-SNAPSHOT.jar
```
