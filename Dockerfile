FROM openjdk:17-oracle
VOLUME /tmp
ADD target/controlecaixa-0.0.1-SNAPSHOT.jar controlecaixa-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY target/controlecaixa-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/controlecaixa-0.0.1-SNAPSHOT.jar"]