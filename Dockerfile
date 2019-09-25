FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9007
ADD /target/docker-demo.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]