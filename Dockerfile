FROM adoptopenjdk/openjdk11
ARG VERSION="latest"
COPY ./build/libs/three-pick-${VERSION}-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
