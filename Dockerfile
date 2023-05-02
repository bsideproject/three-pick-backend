FROM adoptopenjdk/openjdk11
ARG VERSION="latest"
COPY ./build/libs/**.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
