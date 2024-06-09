FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/accounts-payable-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]