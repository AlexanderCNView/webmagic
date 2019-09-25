FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9007
ADD docker-demo.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]