FROM adoptopenjdk/openjdk11
ARG VERSION="latest"
COPY ./build/libs/three-pick-${VERSION}-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
