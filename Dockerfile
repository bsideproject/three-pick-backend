FROM adoptopenjdk/openjdk11
ARG VERSION="latest"
COPY target./build/libs/three-pick-${VERSION}-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
