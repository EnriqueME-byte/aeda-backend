FROM openjdk:11
VOLUME /tmp
EXPOSE 8088
ENV JAVA_OPTS=""
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/ ./urandom -jar /app.jar" ]