FROM adoptopenjdk/openjdk16:alpine-jre

WORKDIR /app
COPY /build/libs/*-all.jar .
RUN mv *-all.jar app.jar
COPY config.properties .

ENTRYPOINT ["java", "-jar", "app.jar"]
