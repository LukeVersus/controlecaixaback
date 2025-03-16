FROM openjdk:17-oracle
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ControlecaixaApplication.jar
ENTRYPOINT ["java","-jar","/ControlecaixaApplication.jar"]