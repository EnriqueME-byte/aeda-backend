FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/sakicorp-0.0.1-SNAPSHOT.jar mi-app.jar
ENTRYPOINT ["java", "-jar", "/Config-server.jar"]