FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9007
ADD /var/lib/jenkins/workspace/springboot/demo/target/demo.jar demo.jar
RUN sh -c 'touch /demo.jar'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /demo.jar" ]