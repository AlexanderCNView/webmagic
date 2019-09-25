FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9007
ADD webmagic-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]