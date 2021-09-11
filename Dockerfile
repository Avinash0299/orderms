FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/orderms-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8200
ENV JAVA_OPTS=""
RUN sh -c "touch orderms-0.0.1-SNAPSHOT.jar"
ENTRYPOINT [ "java", "-jar", "orderms-0.0.1-SNAPSHOT.jar" ]
